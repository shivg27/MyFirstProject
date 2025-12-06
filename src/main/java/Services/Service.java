package Services;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Service {

    public RequestSpecification httpRequest;

    public Service(RequestSpecification requestSpecification){
        httpRequest=requestSpecification;
    }

    public Response sendRequestWithBody(Object body, Method method, String path){
        return httpRequest
                .header("x-api-key","reqres-free-v1").contentType(ContentType.JSON).body(body).request(method,path);
    }
}
