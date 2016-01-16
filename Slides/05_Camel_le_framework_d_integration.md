# Les animations

<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

- [Syntaxe de base](#/1)
- [Code et tableaux](#/2)
- [Positionnement des images](#/3)
- **[Animations et syntaxe avancée](#/4)**



## Fragments

Les items s'affichent au clic. Lors de l'impression, tous les items sont affichés.

Notez que la flèche du bas est transparente lorsqu'il y a des "fragments".

<!-- .element class="fragment" -->
- Item 1

<!-- .element class="fragment" -->
- Item 2

<!-- .element class="fragment" -->
- Item 3

<!-- .element class="fragment" -->
- Item 4



## Fragments

Les items s'affichent au clic. 

Afficher plusieurs éléments en même temps avec `data-fragment-index`.

<!-- .element class="fragment" data-fragment-index="1" -->
- Item 1

<!-- .element class="fragment" data-fragment-index="1" -->
- Item 2

<!-- .element class="fragment" data-fragment-index="2" -->
- Item 3

<!-- .element class="fragment" data-fragment-index="2" -->
- Item 4



## Fragments & Animations

De type Arrivée ou Sortie :

<!-- .element class="fragment fade-in" -->
- fade-in

<!-- .element class="fragment fade-out" -->
- fade-out

<!-- .element class="fragment shrink" -->
- shrink

<!-- .element class="fragment roll-in" -->
- roll-in

Sur des éléments :

<!-- .element class="fragment highlight-current-blue" -->
- highlight-current-blue

<!-- .element class="fragment highlight-green" -->
- highlight-green

<!-- .element class="fragment highlight-red" -->
- highlight-red

<!-- .element class="fragment current-visible" -->
- current-visible



## Alertes

<!-- .element class="alert alert-info"-->
Info / `class="alert alert-info"`

<!-- .element class="alert alert-success"-->
Succès / `class="alert alert-succes"`

<!-- .element class="alert alert-warning"-->
Warning / `class="alert alert-warning"`

<!-- .element class="alert alert-danger"-->
Danger / `class="alert alert-danger"`



## Alertes avec positionnement absolu

<!-- .element class="alert alert-info" style="position: absolute; top: 125px; left: 10px; width: 200px;"-->
Info

<!-- .element class="alert alert-success" style="position: absolute; top: 125px; right: 10px; width: 200px;"-->
Succès

<!-- .element class="alert alert-warning" style="position: absolute; top: 50%; left: 20%; width: 250px;"-->
Warning

<!-- .element class="alert alert-danger" style="position: absolute; top: 50%; left: 60%; width: 150px;"-->
Danger




## Ajouter du HTML 

Il est possible d'inclure directement du code HTML dans le markdown.

<div style="background-color: black; color: white; padding: 5px 20px" class="code">
  &gt; git commit -m "init"<br>
</div>

<br>

Avec le code HTML :
```xml
<div 
    class="code"  
    style="background-color: 
        black; color: white; 
        padding: 5px 20px">
  &gt; git commit -m "init"<br>
</div>
```



<!-- .slide: class="page-title" -->



<!-- .slide: class="page-tp4" -->



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



## 4 – L'utilisation d'aspects AdviceWith

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



## TP 6

<!-- .slide: class="page-tp6" -->
