package br.com.brasilprev.resources;

import br.com.brasilprev.ApplicationTests;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.*;
import io.restassured.RestAssured;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;
import org.apache.commons.lang3.RandomStringUtils;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import br.com.brasilprev.ApplicationTests;
import org.apache.commons.lang3.RandomStringUtils;
import static io.restassured.RestAssured.*;

import org.junit.*;
import org.junit.Test;

import org.testng.Assert;
import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PessoaResourceTest extends ApplicationTests {
	
	public String phone = RandomStringUtils.randomNumeric(8);
	public String cpf = RandomStringUtils.randomNumeric(11);
	public String ddd = RandomStringUtils.randomNumeric(2);
	
	
	@Test
    public void deve1_salvar_suas_pessoas_com_o_mesmo_cpf() {
    	
   	given()
		 .header("Content-type", "application/json")
       .and()
       .body("{"+
      		 "  \"codigo\": 0,"+
      		 "  \"nome\": \"Joao Silva\","+
      		 "  \"cpf\": \"12345678909\","+
      		 "  \"enderecos\": ["+
      		 "    {"+
      		 "      \"logradouro\": \"Rua Alexandre Dumas\","+
      		 "      \"numero\": 123,"+
      		 "      \"complemento\": \"Empresa\","+
      		 "      \"bairro\": \"Chacara Santo Antonio\","+
      		 "      \"cidade\": \"São Paulo\","+
      		 "      \"estado\": \"SP\""+
      		 "    }"+
      		 "  ],"+
      		 "  \"telefones\": ["+
      		 "    {"+
      		 "      \"ddd\": \"11\","+
      		 "      \"numero\": \"985388877\""+
      		 "    }"+
      		 "  ]"+
      		 "}")
		
		
		.when()
		.post("http://localhost:8080/pessoas/11/985388877")
		.then()
		.statusCode(405);
    	
    }
	
	@Test
    public void deve2_salvar_nova_pessoa_no_sistema() {
    	
    	given()
		 .header("Content-type", "application/json")
        .and()
        .body("{"+
       		 "  \"codigo\": 2,"+
       		 "  \"nome\": \"Rafael\","+
       		 "  \"cpf\": \""+cpf+"\","+
       		 "  \"enderecos\": ["+
       		 "    {"+
       		 "      \"logradouro\": \"Rua Alexandr Dumas\","+
       		 "      \"numero\": 13,"+
       		 "      \"complemento\": \"Empresa\","+
       		 "      \"bairro\": \"Chacara Santo Antonio\","+
       		 "      \"cidade\": \"São Paulo\","+
       		 "      \"estado\": \"SP\""+
       		 "    }"+
       		 "  ],"+
       		 "  \"telefones\": ["+
       		 "    {"+
       		 "      \"ddd\": \""+ddd+"\","+
       		 "      \"numero\": \""+phone+"\""+
       		 "    }"+
       		 "  ]"+
       		 "}")
		
		
		.when()
		.post("http://localhost:8080/pessoas/")
		.then()
		.statusCode(201);
    	
    }
	
	
	 
	    
	    @Test
	    public void deve3_salvar_suas_pessoas_com_o_mesmo_telefone() {
	    	
	    	given()
			 .header("Content-type", "application/json")
	      .and()
	      .body("{"+
	     		 "  \"codigo\": 0,"+
	     		 "  \"nome\": \"Rommel Von\","+
	     		 "  \"cpf\": \"12345678909\","+
	     		 "  \"enderecos\": ["+
	     		 "    {"+
	     		 "      \"logradouro\": \"Rua Alexandre Dumas\","+
	     		 "      \"numero\": 123,"+
	     		 "      \"complemento\": \"Empresa\","+
	     		 "      \"bairro\": \"Chacara Santo Antonio\","+
	     		 "      \"cidade\": \"São Paulo\","+
	     		 "      \"estado\": \"SP\""+
	     		 "    }"+
	     		 "  ],"+
	     		 "  \"telefones\": ["+
	     		 "    {"+
	     		 "      \"ddd\": \"11\","+
	     		 "      \"numero\": \"985388877\""+
	     		 "    }"+
	     		 "  ]"+
	     		 "}")
			
			
			.when()
			.post("http://localhost:8080/pessoas/")
			.then()
			.statusCode(400);
	   	
	    	
	    }
	
	
	@Test
	public void deve_procurar_pessoa_pelo_ddd_e_numero_do_telefone() {
		
		Response response = RestAssured.request(Method.GET, "http://localhost:8080/pessoas/11/985388877");
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
		given().
			get("http://localhost:8080/pessoas/11/985388877").
		then().
			statusCode(200)
			.body("telefones[0].ddd", is("11"))
	    	.body("telefones[0].numero", is("985388877"));
	}
	
    @Test
    public void deve_retornar_erro_nao_encontrado_quando_buscar_pessoa_por_telefone_inexistente() throws Exception {
    	
    	given().
		get("http://localhost:8080/pessoas/99/9999999999").
	then().
		statusCode(404);
    	
    }
    
    
    @Test
    public void deve_fitrar_pessoas_pelo_nome() {
    	
    	given().
    	when()
    	.get("http://localhost:8080/pessoas/11/985388877")
    	.then()
    	.statusCode(200)
    	.body("nome", is("Rommel Von"));
    	
    } 
}
