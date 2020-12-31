# cities-suggestions

Geographical Suggestions of Candian Cities according to their longitude and latitude.The data is fetched from cities_canada-usa.tsv which is stored in the AWS S3 bucket. The 'q' parameter can be changed to view the nearby cities which are closely mached with 'q' parameter 

It is developed in Java Spring Boot and deployed on Heroku Cloud. 

# Architecture:

![Flowchart(1)](https://user-images.githubusercontent.com/61722596/103415779-aa81ce80-4b51-11eb-9dd6-4420b60dd99d.png)


Copy and Paste below link in the browser to view the result.

https://citiessuggestions.herokuapp.com/suggestions?q=Lon&latitude=43.70011&longitude=-79.4163

Output would be in JSON as shown in screenshot. Each JSON object has 4 fields: 1. Name of the city 2. Latitude of the city 3. Longitude of the city 4. Score indicates how close is the city to the query parameter.

![image](https://user-images.githubusercontent.com/61722596/103381543-1c0e3e00-4aba-11eb-96c2-7ec7f28a4391.png)


![image](https://user-images.githubusercontent.com/61722596/103381423-b5892000-4ab9-11eb-9033-5003943af2ed.png)


# Deployment Architecture

![Flowchart(2)](https://user-images.githubusercontent.com/61722596/103416028-b326d480-4b52-11eb-89dc-c33f9b56f927.png)
