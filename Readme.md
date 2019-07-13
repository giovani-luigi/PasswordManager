

	Projeto: MyID - Gerenciador de senhas
	Autor: Giovani Luigi R. B. 
	Linguagem: Java 1.8

	NOTAS:
		
		Objetivo:
		
			Implementação de um sistema gerenciador de senhas, com armazenamento criptografado em banco de dados.
		
		Implementação:
		
			O sistema foi implementado utilizando Criptografia AES256 e banco de dados SQLite que dispensa configuração de servidor
			A organização do código permite fácil expansão de diferentes mecanismos de criptografia e de armazenamento.
			Diagrama de classes em anexo.
		
		Idioma:
	
			- Strings que são visiveis ao usuário, incluindo exceções implementadas para interação com a GUI estão em português.
			- Strings relacionadas a exceções utilizadas em loggers, estão em inglês.
		
		Dependências:
			
			Banco de dados:	(Inclusa) 
			- Existe a dependência de uma biblioteca para acesso ao SQLite.
			
			Segurança: (Necessita instalar)
			
				Para utilizar chaves de 256-bits é necessário instalar: "Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files"
			
				As per the Oracle documentation:

					Due to import control restrictions by the governments of a few countries, the jurisdiction policy files shipped with the JDK 5.0 from Sun Microsystems specify that "strong" but limited cryptography may be used.
			
				Para instalar, copie/substitua os arquivos:
				"\policy_file\US_export_policy.jar" e "\policy_file\local_policy.jar" para:
						${java.home}/jre/lib/security/
						e/ou  ${java.home}/jdk/jre/lib/security/
			
		Segurança:
		
			O objetivo é fornecer um robusto mecanismo de criptografia para apenas o campo de senha e usuário no banco de dados.
		
			ATENÇÃO: Quando em funcionamento a GUI é vulnerável a ataque para obter senhas digitadas em campos.
			Devido ao escopo do projeto, não foram utilizadas mecanismos de seguranças necessários para apagar
			as senhas digitadas da memória RAM, e portanto estas permanecerão na memória até que esta seja liberada
			por exemplo, pelo encerramento da aplicação.
			
			Utilizar por conta e risco.
			
			
	--