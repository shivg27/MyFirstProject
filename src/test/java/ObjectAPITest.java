import Services.ObjectService;
import base.BaseAPITest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ObjectAPITest extends BaseAPITest {
    private static final Logger logger = LogManager.getLogger(ObjectAPITest.class);
    ExtentReports extent = getReporterObject();
    ExtentTest test;

    @Test(priority=0, enabled = false,description = "")
    public void verifyGetAllObjectTest() {
        test = extent.createTest("verifyGetAllObjectTest");
        ObjectService objectService = new ObjectService(httpRequest);
        Response getResponse = objectService.getAllObjects();
        test.info("Starting validation of response");
        logger.info("Starting validation of response");
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        test.info("Verified Response Status Code of API");
        logger.info("Verified Response Status Code of API");
        Assert.assertEquals(getResponse.getContentType(), "application/json");
        test.info("Verified Response Content Type of API");
        logger.info("Verified Response Content Type of API");
        Assert.assertTrue(getResponse.getTime()<2000);
        test.info("Verified Response time of API");
        logger.info("Verified Response time of API");
    }

    @Test(priority=1)
    public void verifyGetAllObjectByIDSTest() {
        test = extent.createTest("verifyGetAllObjectByIDSTest");
        ObjectService objectService = new ObjectService(httpRequest);
        Response getResponse = objectService.getAllObjectsByIDS();
        test.info("Starting validation of Response");
        logger.info("Starting validation of Response");
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        test.info("Verified Response Status Code of API");
        logger.info("Verified Response Status Code of API");
        Assert.assertEquals(getResponse.getContentType(),"application/json");
        test.info("Verified Response Content Type of API");
        logger.info("Verified Response Content Type of API");
        Assert.assertTrue(getResponse.getTime()<1000);
        test.info("Verified Response time of API");
        logger.info("Verified Response time of API");
    }

    @Test(priority=2)
    public void verifyGetObjectTest() {
        test = extent.createTest("verifyGetObjectTest");
        ObjectService objectService = new ObjectService(httpRequest);
        Response response = objectService.getObject();
        test.info("Starting validation of Response");
        logger.info("Starting validation of Response");
        Assert.assertEquals(response.getStatusCode(), 200);
        test.info("Verified Response Status Code");
        logger.info("Verified Response Status Code");
        Assert.assertEquals(response.getContentType(),"application/json");
        test.info("Verified Response Content Type");
        logger.info("Verified Response Content Type");
        Assert.assertTrue(response.getTime()<1000);
        test.info("Verified Response Time of API");
        logger.info("Verified Response Time of API");
        JsonPath json = response.jsonPath();
        String id = json.getString("id");
        String name = json.getString("name");
        int year = json.getInt("data.year");
        float price = json.getFloat("data.price");
        String cpuModel = json.getString("data['CPU model']");
        String hardDiskSize = json.getString("data['Hard disk size']");
        Assert.assertEquals(id, "7","ID is mismatched");
        test.info("Verified Value Of Id");
        logger.info("Verified Value Of Id");
        Assert.assertEquals(name, "Apple MacBook Pro 16","Name is mismatched");
        test.info("Verified Name");
        logger.info("Verified Name");
        Assert.assertEquals(year, 2019,"year is mismatched");
        test.info("Verified Year");
        logger.info("Verified Year");
        double delta = 0.01;
        Assert.assertEquals(price, 1849.99,delta,"price is mismatched");
        test.info("Verified Price");
        logger.info("Verified Price");
        Assert.assertEquals(cpuModel, "Intel Core i9","cpuModel is mismatched");
        test.info("Verified CPU Model");
        logger.info("Verified CPU Model");
        Assert.assertEquals(hardDiskSize, "1 TB","hardDiskSize is mismatched");
        test.info("Verified Hard Disk");
        logger.info("Verified Hard Disk");
//        boolean isValid = JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/productSchema.json")
//                .matches(response.getBody().asString());
//        Assert.assertTrue(isValid, "Response JSON does not match the schema");
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schema/productSchema.json"));
        test.info("Verified Json Schema");
        logger.info("Verified Json Schema");
    }

    @Test(priority=3)
    public void addObject() {
        test = extent.createTest("addObject");
        String requestBody = "{\n" +
                "   \"name\": \"Apple MacBook Pro 16\",\n" +
                "   \"data\": {\n" +
                "      \"year\": 2019,\n" +
                "      \"price\": 1849.99,\n" +
                "      \"CPU model\": \"Intel Core i9\",\n" +
                "      \"Hard disk size\": \"1 TB\"\n" +
                "   }\n" +
                "}";
        test.info("Starting validation of Response");
        logger.info("Starting validation of Response");
        ObjectService objectService = new ObjectService(httpRequest);
        Response getResponse = objectService.addObject(requestBody);
        System.out.println(getResponse.asPrettyString());
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        test.info("Verified Response Status Code");
        logger.info("Verified Response Status Code");
        Assert.assertEquals(getResponse.getContentType(),"application/json");
        test.info("Verified Response Content Type");
        logger.info("Verified Response Content Type");
        Assert.assertTrue(getResponse.getTime()<2000);
        test.info("Verified Response Time of API");
        logger.info("Verified Response Time of API");
        JsonPath json = getResponse.jsonPath();
        String id = json.getString("id");
        String name = json.getString("name");
        int year = json.getInt("data.year");
        float price = json.getFloat("data.price");
        String cpuModel = json.getString("data['CPU model']");
        String hardDiskSize = json.getString("data['Hard disk size']");
        String createDate = json.getString("createdAt");
//        Assert.assertEquals(id,"ff8081819782e69e019a508245c4515a","ID is not matched");
        Assert.assertNotNull(id,"ID is not matched");
        test.info("Verified Value Of Id");
        logger.info("Verified Value Of Id");
        Assert.assertEquals(name,"Apple MacBook Pro 16","Name is not matched");
        test.info("Verified Name");
        logger.info("Verified Name");
        Assert.assertEquals(year,2019,"Year is not matched");
        test.info("Verified Year");
        logger.info("Verified Year");
        double delta = 0.01;
        Assert.assertEquals(price,1849.99,delta,"Price is not matched");
        test.info("Verified Price");
        logger.info("Verified Price");
        Assert.assertEquals(cpuModel,"Intel Core i9","CPU Model is not matched");
        test.info("Verified CPU Model");
        logger.info("Verified CPU Model");
        Assert.assertEquals(hardDiskSize,"1 TB","Hard Disk Size is not matched");
        test.info("Verified Hard Disk");
        logger.info("Verified Hard Disk");
        Assert.assertTrue(createDate.startsWith("2025-11-05"),"Created Date is not matched");
        test.info("Verified Created Date");
        logger.info("Verified Created Date");


    }

    @Test(priority=4)
    public void updateObject() {
        test = extent.createTest("updateObject");
        String requestBody = "{\n" +
                "   \"name\": \"Apple MacBook Pro 16\",\n" +
                "   \"data\": {\n" +
                "      \"year\": 2019,\n" +
                "      \"price\": 2049.99,\n" +
                "      \"CPU model\": \"Intel Core i9\",\n" +
                "      \"Hard disk size\": \"1 TB\",\n" +
                "      \"color\": \"silver\"\n" +
                "   }\n" +
                "}";
        test.info("Starting validation of Response");
        logger.info("Starting validation of Response");
        ObjectService objectService = new ObjectService(httpRequest);
        Response getResponse = objectService.updateObject(requestBody);
        System.out.println(getResponse.asPrettyString());
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        test.info("Verified Response Status Code");
        logger.info("Verified Response Status Code");
        Assert.assertEquals(getResponse.getContentType(),"application/json");
        test.info("Verified Response Content Type");
        logger.info("Verified Response Content Type");
        Assert.assertTrue(getResponse.getTime()<2000);
        test.info("Verified Response Time of API");
        logger.info("Verified Response Time of API");
        JsonPath json = getResponse.jsonPath();
        String id = json.getString("id");
        String name = json.getString("name");
        int year = json.getInt("data.year");
        float price = json.getFloat("data.price");
        String cpuModel = json.getString("data['CPU model']");
        String hardDiskSize = json.getString("data['Hard disk size']");
        String color = json.getString("data.color");
        Assert.assertNotNull(id,"ID is not matched");
        test.info("Verified Value Of Id");
        logger.info("Verified Value Of Id");
        Assert.assertEquals(name,"Apple MacBook Pro 16","Name is not matched");
        test.info("Verified Name");
        logger.info("Verified Name");
        Assert.assertEquals(year,2019,"Year is not matched");
        test.info("Verified Year");
        logger.info("Verified Year");
        double delta = 0.01;
        Assert.assertEquals(price,2049.99,delta,"Price is not matched");
        test.info("Verified Price");
        logger.info("Verified Price");
        Assert.assertEquals(cpuModel,"Intel Core i9","CPU Model is not matched");
        test.info("Verified CPU Model");
        logger.info("Verified CPU Model");
        Assert.assertEquals(hardDiskSize,"1 TB","Hard Disk Size is not matched");
        test.info("Verified Hard Disk");
        logger.info("Verified Hard Disk");
        Assert.assertEquals(color,"silver","Color is not matched");
        test.info("Verified Product Color");
        logger.info("Verified Product Color");

    }

    @Test(priority=5)
    public void patchObject() {
        test = extent.createTest("patchObject");
        String requestBody = "{\n" +
                "   \"name\": \"Apple MacBook Pro 16 (Updated Name)\"\n" +
                "}";
        test.info("Starting validation of Response");
        logger.info("Starting validation of Response");
        ObjectService objectService = new ObjectService(httpRequest);
        Response getResponse = objectService.patchObject(requestBody);
        System.out.println(getResponse.asPrettyString());
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        test.info("Verified Response Status Code");
        logger.info("Verified Response Status Code");
        Assert.assertEquals(getResponse.getContentType(),"application/json");
        test.info("Verified Response Content Type");
        logger.info("Verified Response Content Type");
        Assert.assertTrue(getResponse.getTime()<2000);
        test.info("Verified Response Time of API");
        logger.info("Verified Response Time of API");
        JsonPath json = getResponse.jsonPath();
        String name = json.getString("name");
        Assert.assertEquals(name,"Apple MacBook Pro 16 (Updated Name)","Name is not Updated");
        test.info("Verified Name");
        logger.info("Verified Name");
    }

    @Test
    public void deleteObject() {
        test = extent.createTest("deleteObject");
        test.info("Starting validation of Response");
        logger.info("Starting validation of Response");
        ObjectService objectService = new ObjectService(httpRequest);
        Response response = objectService.deleteObject();
        Assert.assertEquals(response.getStatusCode(), 400);
        test.info("Verified Response Status Code");
        logger.info("Verified Response Status Code");
        Assert.assertEquals(response.getContentType(),"application/json");
        test.info("Verified Response Content Type");
        logger.info("Verified Response Content Type");
        Assert.assertTrue(response.getTime()<1000);
        test.info("Verified Response Time of API");
        logger.info("Verified Response Time of API");
        JsonPath json = response.jsonPath();
        String errorMessage = json.getString("error");
        Assert.assertEquals(errorMessage, "Object with id = ff8081819782e69e019a3c9620a31e67 doesn't exist.","Error Message is mismatched");
        test.info("Verified Deleted item");
        logger.info("Verified Deleted item");
    }

    @DataProvider(name = "mapData")
    public Object[][] mapData() {
        Map<String, String> user1 = new HashMap<>();
        user1.put("username", "admin");
        user1.put("password", "123");

        Map<String, String> user2 = new HashMap<>();
        user2.put("username", "user");
        user2.put("password", "456");

        return new Object[][]{
                {user1},
                {user2}
        };
    }
}
