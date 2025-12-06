import Services.SwaggerEditorService;
import base.BaseAPITest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import common.ExcelUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwaggerEditorAPITest extends BaseAPITest {
    private static final Logger logger = LogManager.getLogger(SwaggerEditorAPITest.class);
    ExtentReports extent = getReporterObject();
    ExtentTest test;

    @Test()
    public void UpdateAnExistingPetTest(){
        test = extent.createTest("UpdateAnExistingPetTest");
        String body = "{\n" +
                "  \"id\": 10,\n" +
                "  \"name\": \"leo\",\n" +
                "  \"category\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Dogss\"\n" +
                "  },\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";
        SwaggerEditorService swaggerEditorService = new SwaggerEditorService(httpRequest);
        Response getResponse = swaggerEditorService.UpdateAnExistingPet(body);
        test.info("Starting validation of Response");
        logger.info("Starting validation of Response");
        Assert.assertEquals(getResponse.getStatusCode(),200);
        test.info("Verified Response Status Code");
        logger.info("Verified Response Status Code");
        Assert.assertEquals(getResponse.getContentType(),"application/json");
        test.info("Verified Response Content Type");
        logger.info("Verified Response Content Type");
        Assert.assertTrue(getResponse.getTime()<2000);
        System.out.println(getResponse.asPrettyString());
        JsonPath json = getResponse.jsonPath();
        String id = json.getString("id");
        String name = json.getString("name");
        Integer categoryID = json.getInt("category['id']");
        Integer tagsID = json.getInt("tags[0].id");
        String tagsName = json.getString("tags[0].name");
        String categoryName = json.getString("category['name']");
        String photoUrls = json.getString("photoUrls");
        String status = json.getString("status");
        Assert.assertEquals(id,"10","ID is mismatched");
        test.info("Verified Value Of Id");
        logger.info("Verified Value Of Id");
        Assert.assertEquals(name, "leo","Name is mismatched");
        test.info("Verified Name");
        logger.info("Verified Name");
        Assert.assertEquals(categoryID,1,"category ID is mismatched");
        test.info("Verified Value Of category ID");
        logger.info("Verified Value Of category ID");
        Assert.assertEquals(categoryName, "Dogss","category Name is mismatched");
        test.info("Verified category Name");
        logger.info("Verified category Name");
        Assert.assertEquals(photoUrls, "[string]","photo Urls is mismatched");
        test.info("Verified photo Urls");
        logger.info("Verified photo Urls");
        Assert.assertEquals(tagsID, 0,"tags ID is mismatched");
        test.info("Verified tags ID");
        logger.info("Verified tags ID");
        Assert.assertEquals(tagsName, "string","tags Name is mismatched");
        test.info("Verified tags Name");
        logger.info("Verified tags Name");
        Assert.assertEquals(status, "available","status is mismatched");
        test.info("Verified status");
        logger.info("Verified status");
    }

    @Test()
    public void addANewPetToStoreTest(){
        test = extent.createTest("addANewPetToStoreTest");
        String body = "{\n" +
                "  \"id\": 10,\n" +
                "  \"name\": \"leo\",\n" +
                "  \"category\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Dogs\"\n" +
                "  },\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        SwaggerEditorService swaggerEditorService = new SwaggerEditorService(httpRequest);
        Response getResponse = swaggerEditorService.addANewPetToStore(body);
        test.info("Starting validation of Response");
        logger.info("Starting validation of Response");
        Assert.assertEquals(getResponse.getStatusCode(),200);
        test.info("Verified Response Status Code");
        logger.info("Verified Response Status Code");
        Assert.assertEquals(getResponse.getContentType(),"application/json");
        test.info("Verified Response Content Type");
        logger.info("Verified Response Content Type");
        Assert.assertTrue(getResponse.getTime()<2000);
        System.out.println(getResponse.asPrettyString());

    }

    @Test()
    public void findsPetsByStatusTest(){
        test = extent.createTest("findsPetsByStatusTest");
        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("status","available");
        SwaggerEditorService swaggerEditorService = new SwaggerEditorService(httpRequest);
        Response getResponse = swaggerEditorService.findsPetsByStatus(queryParam);
        test.info("Starting validation of Response");
        logger.info("Starting validation of Response");
        Assert.assertEquals(getResponse.getStatusCode(),200);
        test.info("Verified Response Status Code");
        logger.info("Verified Response Status Code");
        Assert.assertEquals(getResponse.getContentType(),"application/json");
        test.info("Verified Response Content Type");
        logger.info("Verified Response Content Type");
        Assert.assertTrue(getResponse.getTime()<2000);
//        System.out.println(getResponse.asPrettyString());
        JsonPath json = getResponse.jsonPath();
        List<String> statues = json.getList("status");
        List<Long> ids = json.getList("id",Long.class);
        List<String> names = json.getList("name");
        for(Long id : ids)
        {
            Assert.assertNotNull(id);
        }
//        for (String name : names){
//            Assert.assertNotNull(name);
//        }
        for (String status : statues ){
            Assert.assertEquals(status,"available");
        }

    }
    @Test()
    public void findsPetsByTagsTest(){
        test = extent.createTest("findsPetsByTagsTest");
        Map<String,Object> queryParam = new HashMap<>();
        String[] tags = {"tag1"};
        queryParam.put("tags",tags);
        SwaggerEditorService swaggerEditorService = new SwaggerEditorService(httpRequest);
        Response getResponse = swaggerEditorService.findsPetsByTags(queryParam);
        test.info("Starting validation of Response");
        logger.info("Starting validation of Response");
        Assert.assertEquals(getResponse.getStatusCode(),200);
        test.info("Verified Response Status Code");
        logger.info("Verified Response Status Code");
        Assert.assertEquals(getResponse.getContentType(),"application/json");
        test.info("Verified Response Content Type");
        logger.info("Verified Response Content Type");
        Assert.assertTrue(getResponse.getTime()<2000);
        System.out.println(getResponse.asPrettyString());
    }

    @Test()
    public void findPetByIDTest(){
        test = extent.createTest("findPetByIDTest");
        Map<String,Integer> pathParam = new HashMap<>();
        pathParam.put("petId",10);
        SwaggerEditorService swaggerEditorService =new SwaggerEditorService(httpRequest);
        Response getResponse = swaggerEditorService.findPetByID(pathParam);
        test.info("Starting validation of Response");
        logger.info("Starting validation of Response");
        Assert.assertEquals(getResponse.getStatusCode(),200);
        test.info("Verified Response Status Code");
        logger.info("Verified Response Status Code");
        Assert.assertEquals(getResponse.getContentType(),"application/json");
        test.info("Verified Response Content Type");
        logger.info("Verified Response Content Type");
        Assert.assertTrue(getResponse.getTime()<2000);
        System.out.println(getResponse.asPrettyString());
    }
    @Test()
    public void updatesAPetInStoreTest(){
        test = extent.createTest("updatesAPetInStoreTest");
        Map<String,Integer> pathParam = new HashMap<>();
        pathParam.put("petId",10);
        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("name","buso");
        queryParam.put("status","available");
        SwaggerEditorService swaggerEditorService = new SwaggerEditorService(httpRequest);
        Response getResponse = swaggerEditorService.updatesAPetInStore(pathParam,queryParam);
        test.info("Starting validation of Response");
        logger.info("Starting validation of Response");
        Assert.assertEquals(getResponse.getStatusCode(),200);
        test.info("Verified Response Status Code");
        logger.info("Verified Response Status Code");
        Assert.assertEquals(getResponse.getContentType(),"application/json");
        test.info("Verified Response Content Type");
        logger.info("Verified Response Content Type");
        Assert.assertTrue(getResponse.getTime()<2000);
        System.out.println(getResponse.asPrettyString());
    }
    @Test()
    public void deletesAPetTest(){
        test = extent.createTest("deletesAPetTest");
        Map<String,Integer> pathParam = new HashMap<>();
        pathParam.put("petId",16);
        SwaggerEditorService swaggerEditorService = new SwaggerEditorService(httpRequest);
        Response getResponse = swaggerEditorService.deletesAPet(pathParam);

        test.info("Starting validation of Response");
        logger.info("Starting validation of Response");
        Assert.assertEquals(getResponse.getStatusCode(),200);
        test.info("Verified Response Status Code");
        logger.info("Verified Response Status Code");
        Assert.assertEquals(getResponse.getContentType(),"application/json");
        test.info("Verified Response Content Type");
        logger.info("Verified Response Content Type");
        Assert.assertTrue(getResponse.getTime()<2000);
        System.out.println(getResponse.asPrettyString());
    }
    @Test()
    public void uploadsAnImageTest(){
        test = extent.createTest("deletesAPetTest");
        Map<String,Integer> pathParam = new HashMap<>();
        pathParam.put("petId",10);
        File file = new File("src/test/resources/image.JPG");
        SwaggerEditorService swaggerEditorService = new SwaggerEditorService(httpRequest);
        Response getResponse = swaggerEditorService.uploadsAnImage(pathParam,file);
        System.out.println(getResponse.asPrettyString());
        test.info("Starting validation of Response");
        logger.info("Starting validation of Response");
//        Assert.assertEquals(getResponse.getStatusCode(),200);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(getResponse.getStatusCode(),200);
        test.info("Verified Response Status Code");
        logger.info("Verified Response Status Code");
        Assert.assertEquals(getResponse.getContentType(),"application/json");
        test.info("Verified Response Content Type");
        logger.info("Verified Response Content Type");
        Assert.assertTrue(getResponse.getTime()<2000);
        System.out.println(getResponse.asPrettyString());
        sa.assertAll();
    }

    @Test(dataProvider = "loginData")
    public void loginDataProviderTest(String userName, String password){
        logger.info("User Name="+ userName);
        logger.info("Password="+password);
    }

    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][]{
                {"admin123", "pass1"},
                {"admin321", "pass2"},
                {"admin324", "pass3"}
        };
    }

    @Test(dataProvider = "excelData")
    public void excelDataProviderTest(String name, String lastName,String phoneNumber, String email){
        logger.info("First Name="+ name);
        logger.info("Last Name="+lastName);
        logger.info("PhoenNumber="+ phoneNumber);
        logger.info("Email="+email);
    }

    @DataProvider(name = "excelData")
    public Object[][] getExcelData() throws Exception {
        Object[][] data=ExcelUtil.readExcel("src/test/resources/data.xlsx", "Sheet1");
        return data;
    }




    @Test(dataProvider = "mapData", dataProviderClass = ObjectAPITest.class)
    public void testMap(Map<String, String> data) {
        System.out.println(data.get("username") + " | " + data.get("password"));
    }
}
