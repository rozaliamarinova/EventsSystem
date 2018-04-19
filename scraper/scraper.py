#!/usr/bin/python3

from bs4 import BeautifulSoup
import requests
from datetime import datetime
import csv
import MySQLdb

url = "http://www.eventim.bg/bg/tursi/"
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
    return eventCont.h3.text.replace("'", "")

def getEventLocation(event):
    locationInfo = event.find("div", itemprop="location")
    location = {}
    loc = locationInfo.find("strong", itemprop="name")
    if loc is None:
        name = ""
    else:
        name = loc.text
    address = locationInfo.find("address", itemprop="address")
    if address is None:
        city = ""
    else:
        cityAddr = address.find("span", itemprop="addressLocality")
        if cityAddr is None:
            city = ""
        else:
            city = cityAddr.text
    return (name + ":" + city).replace("'", "")
    

def getEventDateTime(event):
    dateTimeInfo = event.findAll("div", class_="List-date")
    if dateTimeInfo is None or not dateTimeInfo :
        date = ""
    else:
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
    if imageLink is None:
        imageLink = ""
    else:
        imageLink = imageLink.find("img").attrs["src"]
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

def parseEvents(rootPageURL):
    parsedEvents = []
        
    while True:
        page = requests.get(rootPageURL)
        soup = BeautifulSoup(page.text, 'html.parser')
        events = soup.find_all("div", class_="List-item")
        for event in events:
            pageURL = baseURL + event.find("a").attrs["href"]
            parsedEvents += parseEventPage(pageURL)

        pagingDivider = soup.find("div", class_="paging-divider")
        if pagingDivider is None:
            break;

        leftEvents = pagingDivider.find("strong", class_="tr_ch").text
        if leftEvents == "0":
            print("no more events")
            break
        print("more events")
        pagingLinkMore = soup.find("a", class_="paging-link--more").attrs["href"]
        rootPageURL += pagingLinkMore
        #print(pageURL)

    return parsedEvents

# print(parsedEvents)

numberEventFeatures = 5
def writeEventToDatabase(dbCursor, event):
    if event is None:
        return
    
    if len(event) > numberEventFeatures:
        return
    
    query = "INSERT INTO `events` (name, place, date, link, image\-link) VALUES("
    query = query +  "'" + event["eventName"] + "'," 
    query = query +  "'" + event["eventLocation"]+  "'," 
    query = query +  "'" + event["datetime"]+  "'," 
    query = query +  "'" + event["pageLink"]+  "'," 
    query = query +  "'" + event["imgLink"]+  "'" 
    query = query + ")"
    try:
        dbCursor.execute(query)
    except Exception as ex:
        template = "An exception of type {0} occurred. Arguments:\n{1!r}"
        message = template.format(type(ex).__name__, ex.args)
        print(message)
        

parsedEvents = parseEvents(url)

host = "localhost"
user = "root"
passwd = "root"
database = "eventapplication"

dbConnection = MySQLdb.connect(host, user, passwd, database)
cursor = dbConnection.cursor()

for event in parsedEvents:
    writeEventToDatabase(cursor, event)

#with open('events.csv', 'w') as csv_file:
#    attributes = ['eventName', 'eventLocation', 'datetime', 'pageLink', 'imgLink']
#    writer = csv.DictWriter(csv_file, attributes, delimiter=';')
#    for row in parsedEvents:
#        writer.writerow(row)

