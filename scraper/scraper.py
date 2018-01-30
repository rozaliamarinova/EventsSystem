
# coding: utf-8

# In[63]:


# from selenium import webdriver
from bs4 import BeautifulSoup
import requests
from datetime import datetime
import csv

page = requests.get("http://www.eventim.bg/bg/tursi/")
soup = BeautifulSoup(page.text, 'html.parser')
baseURL = "http://www.eventim.bg"
eventContent = "List-content"

months = {
    "яну": "01",
    "фев": "02",
    "мар": "03",
    "апр": "04",
    "май": "05",
    "юни": "06",
    "юли": "07",
    "авг": "08",
    "сеп": "09",
    "окт": "10",
    "ное": "11",
    "дек": "12"
}

def getLink(event):
    return event.find("a").attrs['href']

def getEventName(event):
    eventCont = event.find("div", class_=eventContent)
    return eventCont.h3.text

def getEventLocation(event):
    locationInfo = event.find("div", itemprop="location")
    location = {}
    name = locationInfo.find("strong", itemprop="name").text
    address = locationInfo.find("address", itemprop="address")
    city = address.find("span", itemprop="addressLocality").text
#     streetAddress = address.find("meta", itemprop="streetAddress").attrs["content"]
    return name + ":" + city
    

def getEventDateTime(event):
    dateTimeInfo = event.findAll("div", class_="List-date")
    spans = dateTimeInfo[0].findAll("span")
    date = parseDate(spans[0].text)
    if len(spans) > 1:
        time = (spans[1].text).split('\xa0')[1]
        date += " " + str(time)
    return date

def parseDate(dateStr):
    tokens = dateStr.split(' ')
    month = months[tokens[2].lower()]
    day = tokens[1][:2]
    year = tokens[3]
    return year + "-" + month + "-" + day
    
def parseEventPage(pageURL):
    pageSource = requests.get(pageURL).text
    pageSoup = BeautifulSoup(pageSource, 'html.parser')
    imageLink = pageSoup.find("div", class_="pageheader-image pageheader-image--regular")
    if imageLink is not None:
        imageLink = imageLink.find("img").attrs["src"]
    else:
        imageLink = ""
    subEvents = pageSoup.findAll("li", class_="List-item clearfix")
    listEvents = []
    for subEvent in subEvents:
        eventAttrs = {}
        eventAttrs["eventName"] = getEventName(subEvent)
        eventAttrs["eventLocation"] = getEventLocation(subEvent) 
        eventAttrs["datetime"] = getEventDateTime(subEvent)
        eventAttrs["pageLink"] = pageURL
        eventAttrs["imgLink"] = "http:" + imageLink
        listEvents.append(eventAttrs)
    return listEvents

events = soup.find_all("div", class_="List-item")
parsedEvents = []
for event in events:
    pageURL = baseURL + event.find("a").attrs["href"]
    parsedEvents += parseEventPage(pageURL)

# print(parsedEvents)

with open('events.csv', 'w') as csv_file:
    attributes = ['eventName', 'eventLocation', 'datetime', 'pageLink', 'imgLink']
    writer = csv.DictWriter(csv_file, attributes)
    for row in parsedEvents:
        writer.writerow(row)

