<h4>readme.md</h4>
<strong>Tools Used:</strong><br/>
Intellij community version<br/>
maven<br/>
Java 8<br/>
Spring Boot<br/>
Chrome Postman and <strong> curl </strong>for testing rest calls<br/>

How to run DB Shop Finder<br/>

Since you are reading this file, assumed that you have managed to pull it correctly.<br/>

1. Logon to github with your credential<br/>
2. <font style="color:blue">git clone https://github.com/bis-java-git/dbshop.git</font><br/>
3. <font style="color:blue">mvn clean install</font><br/>
4. <font style="color:blue">mvn spring-boot:run</font><br/>

<h2>JSON Scripts</h2><br/>
<font style="color:red">Adding shops</font><br/>
<font style="color:blue">url: localhost:9000/shop</font><br/>
<font style="color:blue">method: post</font><br/>

{"shopName":"BIS Heathrow","shopAddress":{"number":1,"postCode":"TW6"}}<br/>
{"shopName":"BIS Croydon","shopAddress":{"number":3,"postCode":"CR0"}}<br/>
{"shopName":"BIS Balham","shopAddress":{"number":2,"postCode":"SW17"}}<br/>

<font style="color:red">Find shops</font><br/>
<font style="color:blue">url: localhost:9000/shop?latitude=51.1424544&longitude=-0.0642075</font><br/>
<font style="color:blue">method: post</font><br/>

<h4>curl script to find shop example:<br /></h4>
<font style="color:blue"><p>
curl -H "Content-Type: application/json" -X GET http://localhost:9000/shop?latitude=51.1424544&longitude=-0.0642075
</p></font>

<h4>curl script to add shop example:<br /></h4>
<font style="color:blue"><p>
curl -H "Content-Type: application/json" -X POST -d '{"shopName":"BIS Heathrow","shopAddress":{"number":1,"postCode":"TW6"}}' http://localhost:9000/shop
</p></font><br/>
<font style="color:blue"><p>
curl -H "Content-Type: application/json" -X POST -d '{"shopName":"BIS Croydon","shopAddress":{"number":3,"postCode":"CR0"}}' http://localhost:9000/shop
</p></font><br/>
<font style="color:blue"><p>
curl -H "Content-Type: application/json" -X POST -d '{"shopName":"BIS Balham","shopAddress":{"number":2,"postCode":"SW17"}}'  http://localhost:9000/shop
</p></font>



Notes:<br/>
I have used following as my postcode for testing:<br/> 

Base postcode Tunbridge Wells <strong>TN2</strong> (I leave here)<br/>
Heathrow postcode <strong>TW6</strong><br/>
Balham postcode  <strong>SW17</strong><br/>
Croydon postcode  <strong>CR0</strong><br/>