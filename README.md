# Event Management System

İyzico kata about managing ticket sells for a paid conference of the company.

Conference will be held on 27 May 2018.

This is the back-end API of the project. 
Spring Boot, Spring Data Mongo, Spring HATEOAS, Embedded MongoDB is used for the development. 
For payments and BIN Number information Iyzico API services are used.

For CI the project integrated to TravisCI, and project configured to make TravisCI to send JaCoCo reports to
CoverAlls. 

The project is also deployed to Heroku platform, which uploads source from git 
using Heroku CLI.

Project's location on Heroku:
https://event-management-kata.herokuapp.com/

## For the payment operations, the endpoint of the RESTful services:

### https://event-management-kata.herokuapp.com/ticketpayment
POST: Attempts for payment

**Payment request payload example:**

{  
   "paymentCard":{  
      "cardHolderName":"yucel ozan",
      "cardNumber":"5890040000000016",
      "expireYear":"2030",
      "expireMonth":"12",
      "cvc":"123",
      "registerCard":0,
      "cardAlias":null,
      "cardToken":null,
      "cardUserKey":null,
      "metadata":null
   },
   "quantity":1,
   "discountCode":"GAMMA"
}

**Response example:**
{
  "content" : "5526080000000006-1520225130126",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/ticketpayment/5526080000000006-1520225130126"
    }
  }
}



### https://event-management-kata.herokuapp.com/ticketpayment/{id}
GET: Gets the payment information
**Response example:**

{
   "binNumber": "589004",
   "cardType": "DEBIT_CARD",
   "cardAssociation": "MASTER_CARD",
   "cardFamily": "Neo",
   "status": "success",
   "errorCode": null,
   "errorMessage": null,
   "errorGroup": null,
   "locale": "tr",
   "systemTime": 1520183678455,
   "conversationId": "5890040000000016-1520183676267",
   "price": 750,
   "paidPrice": 750,
   "currency": "TRY",
   "installment": 1,
   "paymentId": "10716927",
   "paymentStatus": null,
   "fraudStatus": 1,
   "merchantCommissionRate": 0,
   "merchantCommissionRateAmount": 0,
   "iyziCommissionRateAmount": 19.6875,
   "iyziCommissionFee": 0.22245763,
   "cardToken": null,
   "cardUserKey": null,
   "basketId": "[cardHolderName=yucel ozan,cardNumber=5890040000000016,expireYear=2030,expireMonth=12,cvc=123,registerCard=0]-1520183676596",
   "paymentItems": [   {
      "itemId": "ticket1",
      "paymentTransactionId": "11284678",
      "transactionStatus": 2,
      "price": 750,
      "paidPrice": 750,
      "merchantCommissionRate": 0,
      "merchantCommissionRateAmount": 0,
      "iyziCommissionRateAmount": 19.6875,
      "iyziCommissionFee": 0.22245763,
      "blockageRate": 10,
      "blockageRateAmountMerchant": 75,
      "blockageRateAmountSubMerchant": 0,
      "blockageResolvedDate": "2018-03-11 20:14:38",
      "subMerchantKey": null,
      "subMerchantPrice": 0,
      "subMerchantPayoutRate": 0,
      "subMerchantPayoutAmount": 0,
      "merchantPayoutAmount": 655.09004237,
      "convertedPayout":       {
         "paidPrice": 750,
         "iyziCommissionRateAmount": 19.6875,
         "iyziCommissionFee": 0.22245763,
         "blockageRateAmountMerchant": 75,
         "blockageRateAmountSubMerchant": 0,
         "subMerchantPayoutAmount": 0,
         "merchantPayoutAmount": 655.09004237,
         "iyziConversionRate": 0,
         "iyziConversionRateAmount": 0,
         "currency": "TRY"
      }
   }],
   "connectorName": null,
   "authCode": "313366",
   "phase": "AUTH",
   "lastFourDigits": "0016",
   "_links": {"self": {"href": "http://localhost:8080/ticketpayment/10716927"}}
}

## For the Bin Number operations:

### https://event-management-kata.herokuapp.com/binnumber

GET: Retrieves BIN Number information
**Response example**
{
   "errorCode": null,
   "locale": "tr",
   "conversationId": "552608",
   "cardAssociation": "MASTER_CARD",
   "errorMessage": null,
   "errorGroup": null,
   "systemTime": 1520226562106,
   "status": "success",
   "cardFamily": "Axess",
   "cardType": "CREDIT_CARD",
   "commercial": 1,
   "bankCode": 46,
   "bankName": "Akbank",
   "_links": {"self": {"href": "http://localhost:8080/binnumber/552608"}},
   "binNumber": "552608"
}


## Validations on payments

For different dates there are different prices for the tickets:

01 JUN 2017 	- 	15 JAN 2018 		Blind Bird 		250 TL
16 JAN 2018 	-	28 FEB 2018 		Early Bird 		500 TL
01 MAR 2018  	-	30 APR 2018 		Regular 		750 TL
01 MAY 2018 	-	27 MAY 2018 		Laggard 		1000 TL

For some specific dates there are discount codes:

GAMMA 		13 Mart 		%10
BECK 		31 Mart 		%15
CUNNINGHAM 	26 Mayıs 		%5
AGILE 		11-13 Şubat 	%20

 
## Properties

Application properties are on classpath, on "application.properties"
But if you want to pass your own configuration file, you can add a JVM parameter named "config.dir" on 
execution:

event-management-system-0.0.1-SNAPSHOT.jar java -jar **-Dconfig.dir=property/file/path/config.txt**



