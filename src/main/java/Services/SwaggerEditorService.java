package Services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Map;

public class SwaggerEditorService extends Service {

    public String PET_BASE_PATH = "pet";
    public String PET_FINDBYSTATUS_BASE_PATH = "/pet/findByStatus";
    public String PET_FINDBYTAGS_BASE_PATH = "/pet/findByTags";
    public String PET_PETID_BASE_PATH = "/pet/{petId}";
    public String PET_UPLOAD_IMAGE_BASE_PATH = "/pet/{petId}/uploadImage";

    private static final Logger logger = LogManager.getLogger(SwaggerEditorService.class);
    public SwaggerEditorService(RequestSpecification httpRequest) {
        super(httpRequest);
    }

    public Response UpdateAnExistingPet(String body) {
        logger.info("Start making API put call");
        return httpRequest.contentType(ContentType.JSON).body(body).put(PET_BASE_PATH);
    }

    public Response addANewPetToStore(String body){
        logger.info("Adding new pet");
        return httpRequest.contentType(ContentType.JSON).body(body).post(PET_BASE_PATH);
    }

    public Response findsPetsByStatus(Map<String,String> queryParam){
        logger.info("Verifying grt response");
        return httpRequest.request().queryParams(queryParam).get(PET_FINDBYSTATUS_BASE_PATH);
    }

    public Response findsPetsByTags(Map<String,Object> queryParam){
        logger.info("Finding Pet by there tags");
        return httpRequest.request().queryParams(queryParam).get(PET_FINDBYTAGS_BASE_PATH);
    }
    public Response findPetByID(Map<String,Integer> pathParam){
        logger.info("Finding Pet by there ID's");
        return httpRequest.request().pathParam("petId", pathParam.get("petId")).get(PET_PETID_BASE_PATH);
    }

    public Response updatesAPetInStore(Map<String,Integer> pathParam,Map<String,String> queryParam){
        logger.info("Adding new pet");
        return  httpRequest.contentType(ContentType.JSON).pathParam("petId",pathParam.get("petId")).queryParams(queryParam).post(PET_PETID_BASE_PATH);
    }
    public Response deletesAPet(Map<String,Integer> pathParam){
        logger.info("Deleting Pet by there ID's");
        return httpRequest.request().pathParam("petId", pathParam.get("petId")).delete(PET_PETID_BASE_PATH);
    }
    public Response uploadsAnImage(Map<String,Integer> pathParam, File file){
        logger.info("Finding Pet by there ID's");
        return httpRequest.pathParam("petId",10).header("accept", "application/json")
                .header("Content-Type", "application/octet-stream")
                .body(file).post(PET_UPLOAD_IMAGE_BASE_PATH);
    }

}
