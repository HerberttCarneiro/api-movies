# Api de filmes
Esse projeto foi criado utilizando a documentação do site [https://www.eclipse.org/](https://www.eclipse.org/community/eclipse_newsletter/2020/february/4.php.)

O servidor utilizado nesse projeto foi WildFly seguindo o tutorial [Deploying and Debugging Your Jakarta EE Application With WildFly and IntelliJ](https://www.youtube.com/watch?v=l4uAJlvb9IY)

# Configurando datasource no widfly
- Baixe o servidor widfly na versão **18.0.1.Final** no site https://wildfly.org/downloads/.
- Instalação e Configuração do Driver JDBC

Considerando que você já tem o wildfily instalado. Acesse a pasta onde está o servidor “wildfly-18.0.1.Final/modules/system/layers/base/com”.
Crie a pasta “mysql” e dentro dela a pasta “main”. Nesta pasta crie o um arquivo chamado “module.xml”. Seu conteúdo será igual ao seguinte:
```
<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.1" name="com.mysql">
    <resources>
        <resource-root path="mysql-connector-java-5.1.45-bin.jar"/> 
    </resources>
    <dependencies>
        <module name="javax.api"/>
        <module name="javax.transaction.api"/>
    </dependencies>
</module>
```
- Criação do Datasource

Acesse novamente a pasta onde foi instalado o Servidor Wildfly “wildfly-18.0.1.Final/standalone/configuration/” e abra o arquivo: “standalone.xml”
Procure a ocorrência da tag que indica onde devemos adicionar os datasources ”

```
<datasource jndi-name="java:jboss/datasources/MysqlDS" pool-name="MysqlDS" enabled="true" use-java-context="true">
    <connection-url>jdbc:mysql://127.0.0.1:3306/movies</connection-url>
    <driver>com.mysql</driver>
    <transaction-isolation>TRANSACTION_READ_COMMITTED</transaction-isolation>
    <pool>
        <min-pool-size>5</min-pool-size>
        <max-pool-size>100</max-pool-size>
        <prefill>true</prefill>
        <use-strict-min>false</use-strict-min>
        <flush-strategy>FailingConnectionOnly</flush-strategy>
    </pool>
    <security>
        <user-name>{USUARIO}</user-name>
        <password>{SENHA}</password>
    </security>
    <statement>
        <prepared-statement-cache-size>32</prepared-statement-cache-size>
    </statement>
</datasource>
```

Logo abaixo do datasource existe a tag “drivers” onde precisamos adicionar a referência ao driver JDBC do MySQL que criamos.

```
<driver name="com.mysql" module="com.mysql">
    <driver-class>com.mysql.jdbc.Driver</driver-class>
</driver>
```
# Configurar Lombok

Para configurar o lombok no seu ambiente utilize esse documento [Using Lombok](https://projectlombok.org/setup/overview)

# Persistence.xml

As configurações estão feitas para apagar e criar o banco de dados todas vez que aplicação subir