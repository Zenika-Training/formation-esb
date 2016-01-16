# Apache Camel

<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

- [00 – Agenda](#/0)
- [01 – De la difficulté de définir un ESB](#/1)
- [02 – Comprendre les principes fondamentaux d'un ESB](#/2)
- [03 – L'architecture d'un ESB](#/3)
- [04 – Un ESB Open Source](#/4)
- [05 – Apache ServiceMix](#/5)
- **[06 – Apache Camel](#/6)**
- [07 – Apache ActiveMq](#/7)
- [08 – Connectivité](#/8)
- [09 – Routage, transformations et intégration](#/9)
- [10 – Web Services](#/10)
- [11 – Déployer un environnement de qualité, robuste et fiable](#/11)
- [12 – Surveiller l'ESB : Monitoring métier et technique](#/12)



## Contenu

- Présentation du framework Camel
- Configuration
- Exchange, Messages, Headers, Id, ... les objets de Camel
- Tests
- Intégration à Apache ServiceMix


## Présentation du framework Camel

- Apache Camel

- Implémentation des EIP 

- Définition des routes
	- DSL (recommandé)
	- XML

- Framework
	- Pas un conteneur
	- Pas un serveur

- Peut être intégré
	- ServiceMix
	- ActiveMQ


## Présentation du framework Camel

10 bonnes raisons d'adopter un chameau :

- Excellent framework d'intégration
- Open source et gratuit  (Apache Software Foundation)
- Support de 50 EIP
- Support de plus de 70 types de Endpoint (connecteurs)
- Création de règles très intuitives
- Basé sur le framework Spring
- Léger et puissant
- Très bonne intégration à ServiceMix & ActiveMQ
- Excellente documentation
- Support de 19 langages (pour expressions et prédicats)

TODO by Olivier : vérifier le nombre d'EIP, endpoints, langages



<!-- .slide: class="page-questions" -->



## Apache Camel et Spring

- Camel étend Spring

- La configuration XML de Camel est basée sur Spring

- Camel utilise le support Spring pour :
	- La gestion des transactions
	(TODO : check mais il y a aussi la dépendance d'injections et la gestion de propriétés)
- Composants Spring accessibles dans Camel

```xml

	<beans xmlns="http://www.springframework.org/schema/beans">
		
		<camelContext id="camel"
			xmlns="http://camel.apache.org/schema/spring">
			<package>com.resanet.camel</package>
		</camelContext>
	
	</beans>
```	



## Apache Camel et Spring






## Fonctionnement de Camel

Camel permet de relier des Endpoints via des routes :
- Statiques
- Dynamiques
- Simples
- Complexes

Camel permet de définir des endpoints : 
- Associés à des ressources
- Physiques ou logiques

Les endpoints et les routes sont définis dans le CamelContext (moteur Camel)



## Fonctionnement de Camel

Exemple de route DSL Java & Xml

```java
	from("direct:orig").to("direct:dest");
```

```xml
	<from uri="direct:orig" />
	<to uri="direct:dest" />
```

- Définition et création du endpoint « direct:orig »
- Permet de relier les endpoints logiques Camel « direct:orig » au endpoint « direct:dest »
- Représente une route à part entière

		
## Fonctionnement de Camel

- Définir les routes en DSL
- Hériter de la classe org.apache.camel.builder.RouteBuilder
- Redéfinir la méthode abstraite configure()
- Un point d'entrée unique from() pour chaque route

```java
	
	import org.apache.camel.builder.RouteBuilder;
	
	public class MaRoute extends RouteBuilder {
	
			@Override
			public void configure() throws Exception {
				from("direct:origine")
				.to("direct:destination");
			}
	}
	
```



## Fonctionnement de Camel

Création des routes : 

- L'appel de la méthode from() (qui doit être unique au sein d'une route) retourne un « processor type »

- Ce processor est l'action suivante qui doit être effectuée pour poursuivre l'exécution de la route

- Différents processors existent par défaut (to, filter, etc.)

- Il est possible de définir ses propres processors


## Fonctionnement de Camel

TODO parler des enpoints de type direct, SEDA, ...




<!-- .slide: class="page-questions" -->




## Possibilités de configuration

... si on a pratiqué le métier après 2004 et qu'on sait ce qu'est une annotation

TODO : issue #

XML
Spring



<!-- .slide: class="page-questions" -->



## Exchange, Messages, Headers, Id, ... les objets de Camel

Présenter les possiblilités d'accéder à :
- le contenu des Exchanges
- Message, Body, Headers, id
- les Logs



<!-- .slide: class="page-questions" -->




## L'intérêt des tests

- Tests unitaires ou d'intégration ?
- Bonne pratiques de tests avec Camel

Contenu

Notes :



## L'intégration entre plusieurs applications présente de nombreuses contraintes pour les tests

- Les tests permettent de valider l'architecture globale
- A cause des contraintes d'intégration, il peut être compliqué de tester le flux réel.

L'importance des tests

Notes :



## Les tests unitaires permettent de valider le fonctionnement du code au court du développement.

- Les tests d'intégration utilisent généralement l'application déployée et valident des scénarii de bout en bout
- Possibilité de tester plus facilement les cas aux limites et l’adhérence aux réseaux (timeout, proxy, etc)

Tests unitaires ou d'intégration ?

Notes :



## Facilitez-vous les tests !

- En Camel, comme en Java, la testabilité du code dépend de votre architecture logicielle et du respect des principes OO
- SOLID :
  - Single responsibility
  - Open-closed
  - Liskov substitution,
  - Interface segregation
  - Dependency inversion

Notes :



## Le framework Camel Test

- Les tests de route Camel se résument généralement à :
  - Envoyer/recevoir un message
  - Vérifier son contenu et les entêtes
  - Compter le nombre de messages reçus
  - Vérifier le comportement des routes d'exception

Notes :



## Le framework Camel-Test

- Camel propose une API dédiée aux tests
- Les Mock Endpoints
  - Se substituent à un endpoint existant
  - Tracent les messages reçus
  - Simule des comportements

Notes :



## Le framework Camel Test

- Les tests sont structurés de la manière suivante :
  - Prépare les mocks :
    - Enregistre les attentes expectedXxx
    - Enregistre les comportements whenXxx

  - Exécute la route
  - Attend que les mocks aient réalisés leurs attentes (timeout)

```java
// Mise en place des assertions
mockEndpointInput.expectedBodiesReceived("Hello World");
mockEndpointOutput.expectedBodiesReceived("Hello World");

// Envoi du message en entré de la route
template.sendBody("direct:start", "Hello World");

// Validation de toutes les assertions
assertMockEndpointsSatisfied();
```

Notes :



## Les différentes stratégies de tests

- La testabilité de vos routes Camel dépend :
  - Du découpage de vos flux
  - Du degré de configuration de votre application
  - De votre version de Camel

Notes :



## Exemple

- Soit la route suivante à tester :

```java
public class FooRouteBuilder extends RouteBuilder {

  public void configure() throws Exception {

    from("direct:input")
      .to("log:foo")
      .to("file:///data/output");
  }
}
```

Notes :



## CamelTestSupport

- Le module camel-test fournit une classe de base pour les tests
- Un producerTemplate protected
- Des méthodes à surcharger :
  - `createRouteBuilder()`
    - Pour donner la route à tester

  - `isMockEndpoint()`
    - Pour donner les URI à mocker

  - `getMockEnpoint()`
    - Pour récuperer le mock
- ...

Notes :



## Utilisation de CamelTestSupport

```java
public class FooTest extends CamelTestSupport {

  @Override
  public RouteBuilder createRouteBuilder() {
    return new FooRouteBuilder();
  }

  @Test
  public void test() throws Exception {

    getMockEndpoint("mock:output").expectedMessageCount(1);

    template.sendBody("direct:input", "Hello World");

    assertMockEndpointsSatisfied();
  }
}
```

Notes :



## Remplacer un endpoint par Mock

- Plusieurs stratégies :
  - Rendre la route configurable
  - Utiliser les aspects ("advices")

Notes :



## Les routes configurables

```java
public class FooRouteBuilder extends RouteBuilder {
  public void configure() throws Exception {
    from("{{inputUri}}")
      .to("log:foo")
      .to("{{outputUri}}");
    }
}
```

```java
public class FooRouteBuilder extends RouteBuilder {
  private String inputUri;
  private String outputUri;
  public void configure() throws Exception {
    from(inputUri)
      .to("log:foo")
      .to(outputUri);
  }
}
```

```
inputURI=file:/opt/inputDirectory?delete=true
outputURI=jms:queue:outputQueue
```

Notes :



## Les routes configurables

- Avantages :
  - Changement des endpoints pour des direct ou des mocks

- Inconvénients :
  - Ça n'est pas la route réelle qui est testée
  - Erreur dans la configuration détectable qu'au démarrage
  - La lisibilité est moindre

Notes :



## Utilisation d'aspects AdviceWith

```java
public void testAdvised() throws Exception {
  context.getRouteDefinitions().get(0)
    .adviceWith(context, new AdviceWithRouteBuilder() {
      @Override
      public void configure() throws Exception {
      interceptSendToEndpoint("file:///data/output")
        .skipSendToOriginalEndpoint()
        .to("mock:output");
      }
    }
  );

  getMockEndpoint("mock:output").expectedMessageCount(1);
  context.start();

  template.sendBody("direct:input", "Hello World");

  assertMockEndpointsSatisfied();
}

@Override
public boolean isUseAdviceWith() {
  return true;
}
```

Notes :



## Utilisation d'aspects AdviceWith

- Remplacer tous les endpoints
  - Utiliser mockEndpoints* dans le AdviceWithRouteBuilder
  - Surcharger CamelTestSupport.isMockEndpoints

```java
new AdviceWithRouteBuilder() {
  @Override
  public void configure() throws Exception {
    mockEndpointsAndSkip("file:.*");
  }
}
```

```java
@Override
public boolean isMockEndpoints() {
  return "file:.*";
}
```

Notes :



## L'utilisation d'aspects AdviceWith

- Avantages :
  - Changement des endpoints physiques par des direct ou des mocks

- Inconvénients :
  - Nécessite beaucoup de code de tests

Notes :



## Simuler une erreur ou une réponse

- Enregistrer des comportements sur un mock endpoint avec les whenXxx

```java
getMockEndpoint("mock:output").whenAnyExchangedReceived(
  new Processor() {
    public void process(Exchange e) throws Exception {
      throw new Exception("Serveur indisponible");
    }
  }
);
```

Notes :



## Vérifier les messages

- Enregistrer les attentes
- Vérifier les mocks

```java
mockEndpoint.expectedMessageCount(int)
mockEndpoint.expectedMinimumMessageCount(int)
mockEndpoint.expectedBodiesReceived(...)
mockEndpoint.expectedHeaderReceived(...)
mockEndpoint.expectsAscending(Expression)
mockEndpoint.expectsNoDuplicates(Expression)
mockEndpoint.expects(Runnable)
```

```java
mockEndpoint.allMessages().body()…
mockEndpoint.message(0).header("h")…

mockEndpoint.getReceivedExchanges()
mockEndpoint.getReceivedCounter()
mockEndpoint.getFailures()
```

Notes :



## Camel Test Spring

- Dans le module `camel-test-spring` :
  - `CamelSpringTestSupport`
    - Etend `CamelTestSupport`
    - Surcharger `createApplicationContext()`

  - `CamelSpringJUnit4ClassRunner`
    - Etend `SpringJUnit4ClassRunner`
    - Ajouter annotations :
      - `@EndpointInject` Endpoint
      - `@Produce` ProducerTemplate
      - `@MockEndpoints(regexp)`
      - `@MockEndpointsAndSkip(regexp)`
      - `@UseAdviceWith`
      - `@DisableJmx`

Notes :



## Camel Test Spring

- Modification au runtime des endpoints
- Auto Mocking des endpoints
- Littéralement « Rediriger telle URI vers telle URI »

```java
@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/META-INF/spring/camel-context.xml")
@MockEndpointsAndSkip
public class MyRouteTest {

  @EndpointInject(uri = "mock:direct:ouput")
  MockEndpoint outputEndpoint;

  @Produce(uri = "direct:input")
  ProducerTemplate inputProducer;

}
```

Notes :



## Camel Test Spring

- Avantages :
  - Tests sous forme de JUnit avec @nnotations

- Inconvénients :
  - Beaucoup d'@nnotations

Notes :



## Tests d'intégration complets

- Lancer l'application complète
  - Avec Spring

- Utiliser Camel Test pour
  - Emettre des messages vers des endpoints physiques
  - Simuler des systèmes tiers

- Ou bien, utiliser des frameworks de tests d'intégration
  - SoapUI pour les WS
  - Citrus Framework

```java
public static void main(String[] args) {
  Main.main(args);
}
```

Notes :



## Tests d'intégration complet

- Avantages :
  - Teste l'application complète

- Inconvénients :
  - Besoin des applications à intégrer
  - Complexité dépendante des types de endpoints
  - Pas de flexibilité sur la manipulation du flux
  - Diagnostique des erreurs
  - Pas d'automatisation

Notes :



<!-- .slide: class="page-questions" -->



## Intégration de Camel à ServiceMix

Configuration et utilisation de Camel : 
- Déploiement d'un fichier xml avec le DSL Xml
- Création d'un jar contenant les classes Java Camel et un fichier camel-context.xml
- Déclaration du package Java où sont situées les routes

```xml

	<beans xmlns="http://www.springframework.org/schema/beans">
		
		<camelContext id="camel"
			xmlns="http://camel.apache.org/schema/spring">
			<package>com.resanet.camel</package>
		</camelContext>
	
	</beans>
```

## Intégration de Camel à ServiceMix

Todo détailler d'avantage cela suicite pleins de questions inutiles
- Comment on configure un Bundle ...
- Le plugin Eclipse
- ...



<!-- .slide: class="page-questions" -->



## TP 2

<!-- .slide: class="page-tp2" -->
