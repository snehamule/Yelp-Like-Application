This is replica of yelp application.
User can select business and review based on various catagory. User can narrow down search using facet search.
Yelp dataset has used a smaller version of real Yelp dataset 
(The dataset which Yelp.com has announced the “Yelp Dataset Challenge”) containing 42K businesses, 252K users, and 1.1M reviews.
UI is designed using Java Swing and Oracle 11g Database is used for back-end data storage.
Designed ER diagram, schema design for an application 
ORACLE Database |JDBC connection | Swing GUI

Language : Java , Java swing
Database : Oracle 11g

# YELP like Application

This is replica of yelp application. It will display business and reviews of buisiness to user according to serach criteria. This application allow user to select business and review based on various catagory.Also user can narrow down search using facet search .


## Description: 
When user load this appliation , then categories will display on the UI. When user select category,subcategories related to categories will display. When user select subcategories then attributes in the catagories will display. When user select attributes then business will display. For each while showing catogory to sybcatogy it is optional to user to choose subcatagories or attributes. User can narrow down search by open and close time of business, city , or state. For ecah panel (Catagories, Subcatagories, Attribute) user can select AND or OR operation between two panel.Default operation in the panel is or operation. Data is in json format. In this application initially data is store in oracle database and then fetch data according to serach criteria <br />

## Technology used: <br />
Java , Java swing <br />

## Setup required:<br />
Java version: 1.8 or greater<br />
External jars : json-simple-1.1.jar


## Download json-simple-1.1.jar />
In this project Json-simple is used to parse json file. 
Download json-simple-1.1 [Downalod Json Simple-1.1](http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm)


## Dataset :<br />
This application used dataset which Yelp.com has announced the “Yelp Dataset Challenge”. It contains 42K businesses, 252K users, and 1.1M reviews. Data folder conatins business.json, rerview.json, user.json,business_category.json files.
User can also download those files from  [Yelp database challenge](https://www.yelp.com/dataset).  

## Run program : <br />
1. Download code from git  using  git clone .
2. Place downloaded dataset files in the same folder
3. For Process the Data run command 
```
	python process_data.py
```	
4. To start a recommendation system run command 
```
	python start_page.py
```
5. Enter option 1 to display popular book 2 to book similar to your choice
6. If user select option one then popular books will display
7. If user select option 2, app ask to enter book name .
8. App will recommend some books but it is optional to user to choose books from the suggested book. Once user enter book app    will display recommended books
