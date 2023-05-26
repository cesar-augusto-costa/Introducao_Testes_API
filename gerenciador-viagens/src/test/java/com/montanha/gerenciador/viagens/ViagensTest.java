package com.montanha.gerenciador.viagens;

import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ViagensTest {
    @Test
    public void testDadoUmAdministradorQuandoCadastroViagensEntaoObtenhoStatusCode201() {
        // Configurar o caminho comum de acesso a API Rest
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        // Login na API Rest com Administrador
        String token = given()
                .body("{\n" +
                        "  \"email\": \"admin@email.com\",\n" +
                        "  \"senha\": \"654321\"\n" +
                        "}")
                .contentType(ContentType.JSON)
            .when() //quando
                .post("/v1/auth")
            .then() //então
                .log().all()
                .extract()
                .path("data.token");

        //System.out.println(token); // mostrar o token

        // Cadastrar viagem
        given()
                .header("Authorization", token )
                .body("{\n" +
                        "  \"acompanhante\": \"Nayara\",\n" +
                        "  \"dataPartida\": \"2023-03-23\",\n" +
                        "  \"dataRetorno\": \"2023-04-01\",\n" +
                        "  \"localDeDestino\": \"Manaus\",\n" +
                        "  \"regiao\": \"Norte\"\n" +
                        "}")
                .contentType(ContentType.JSON)
            .when()
                .post("/v1/viagens")
            .then()
                .log().all()
                .assertThat()
                    .statusCode(201);
    }

}
