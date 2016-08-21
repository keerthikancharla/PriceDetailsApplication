# PriceDetailsService

PriceDetailsService is a restful service which retrieves,updates,deletes and inserts product price for a given product based on productID.

## Usage
* GET '/prices': Get all price details.
* GET '/price/productId': get the price info details of the passed product id.
* DELETE '/price/productId': delete the price info of the passed product id.
* POST '/price': create the price info with posted data.
* PUT '/price': update the price info of the product id posted.
* PUT '/prices': update the price info of the list of products.

#### Prerequisites
- Java 8
- Maven > 3.0
- MySql > 5.7

### Build and Run

#### From terminal
Go to projects root folder and then type:
```$mvn spring-boot:run```

#### From Eclipse |Spring Tool Suite |IntelliJ
Import as *Existing Maven Project* and run it as *Spring Boot App*

#### Configurations
Open the `application.properties` file and set your own configurations