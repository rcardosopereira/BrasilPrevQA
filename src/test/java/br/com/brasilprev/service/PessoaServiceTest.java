package br.com.brasilprev.service;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {
	
	public String phone = RandomStringUtils.randomNumeric(8);
	public String cpf = RandomStringUtils.randomNumeric(11);
	public String ddd = RandomStringUtils.randomNumeric(2);

	@Before
	public void setUp() {
	}

	@Test
	public void deve_salvar_pessoa_no_repositorio() throws Exception {
		
		given()
		 .header("Content-type", "application/json")
       .and()
       .body("{"+
      		 "  \"codigo\": 2,"+
      		 "  \"nome\": \"Pessoa\","+
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
	public void nao_deve_salvar_duas_pessoas_com_o_mesmo_cpf() throws Exception {
		
		given()
		 .header("Content-type", "application/json")
      .and()
      .body("{"+
     		 "  \"codigo\": 2,"+
     		 "  \"nome\": \"Pessoa\","+
     		 "  \"cpf\": \"12345678909\","+
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
		.statusCode(400);
		
	}

	@Test
	public void nao_deve_salvar_duas_pessoas_com_o_mesmo_telefone() throws Exception {
		
		given()
		 .header("Content-type", "application/json")
      .and()
      .body("{"+
     		 "  \"codigo\": 2,"+
     		 "  \"nome\": \"Pessoa\","+
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
	public void deve_retornar_execao_de_nao_encontrado_quando_nao_existir_pessoa_com_o_ddd_e_numero_de_telefone() throws Exception {
		
		given().
    	when()
    	.get("http://localhost:8080/pessoas/51/991")
    	.then()
    	.statusCode(404);
    	//.body("telefones[0].ddd", is(51))
    	//.body("telefones[0].numero", is(991));
		
	}
	
	@Test
    public void deve_retornar_dados_do_telefone_dentro_da_excecao_de_telefone_nao_encontrado_exception() throws Exception {
		
		given().
    	when()
    	.get("http://localhost:8080/pessoas/00/01234")
    	.then()
    	.statusCode(404);
    	//.body("telefones[0].ddd", is(51))
    	//.body("telefones[0].numero", is(991));
		
		
    }
	
	@Test
	public void deve_procurar_pessoa_pelo_ddd_e_numero_do_telefone() throws Exception {
		
		given().
    	when()
    	.get("http://localhost:8080/pessoas/11/985388877")
    	.then()
    	.statusCode(200)
    	.body("telefones[0].ddd", is("11"))
    	.body("telefones[0].numero", is("985388877"));
		
	}
}
