# Déployer un environnement de qualité, robuste et fiable

<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

- [00 – Agenda](#/0)
- [01 – De la difficulté de définir un ESB](#/1)
- [02 – Comprendre les principes fondamentaux d'un ESB](#/2)
- [03 – L'architecture d'un ESB](#/3)
- [04 – Un ESB Open Source](#/4)
- [05 – Apache ServiceMix](#/5)
- [06 – Apache Camel](#/6)
- [07 – Apache ActiveMq](#/7)
- [08 – Connectivité](#/8)
- [09 – Routage, transformations et intégration](#/9)
- [10 – Web Services](#/10)
- **[11 – Déployer un environnement de qualité, robuste et fiable](#/11)**
- [12 – Surveiller l'ESB : Monitoring métier et technique](#/12)



## Contenu

- Gestion des erreurs
- Gestion des transactions
- Sécuriser des composants
- Clustering et haute disponibilité

Notes :



## Déployer un environnement de qualité, robuste et fiable :

Gestion des erreurs

Notes :



## Gestion des erreurs

- On parle d'erreurs en Camel quand un événement inattendu survient
  - Sur un composant (ex appel JMS ou BDD)
  - Au sein du code d'un bean

- Techniquement, cela correspond à une exception non «catchée» qui remonte jusqu'à la route

Notes :



## Gestion des erreurs

Les questions à se poser pour la gestion d'erreur :

1. Suis-je dans un contexte transactionnel ?
  - La gestion d'erreurs est très différente (cf. section sur les transactions)
2. Quel est mon Message Exchange Pattern ?
  - Comment remonter l'erreur à l'appelant ? Aux autres composants ?
  - Comment garder une trace de l'échec ?
3. Mon erreur est-elle remédiable ou non ?
  - Faut-il mettre un mécanisme de rejeu ?

Notes :



## Gestion des erreurs métiers : MEP

- En cas d'erreur sur un composant synchrone
  - Message Exchange Pattern : In Out/Fault

Validation SEValidationHTTPinoutValidation SEValidationHTTPinfault

Notes :



## Gestion des erreurs métiers : MEP

- En cas d'erreur sur un composant asynchrone
  - Message Exchange Pattern : InOnly

Validation SEValidationHTTPinValidation SEValidationHTTPinfault?

Notes :



## Gestion des erreurs : remédiable ?

- Les erreurs remédiables :
  - Ex : Base temporairement inaccessible, serveur JMS down, partenaire extérieur rendant une erreur technique, réseau inaccessible, erreur HTTP 50X=> Quelle politique de rejeu ?

- Les erreurs non-remédiables :
  - Ex : Requête entrante xml non valide, paramètre d'entrée excédant des valeurs max, table SQL manquante, erreur HTTP 40X=> Comment notifie-t-on l'appelant ?

Notes :



## Gestion des erreurs : remédiable ?

- Camel représente une erreur remédiable par une Exception positionnée dans l'Exchange grâce à :
  - NB : les throwables sont wrappés par une exception
  - Une exception jetée non catché

- Une erreur non-remédiable est représentée par un flag :
  - Une erreur non-remédiable ne sera pas rejouée ou routée plus loin

```java
Message msg = Exchange.getOut();
msg.setFault(true);
msg.setBody("Unknown customer");
```

```java
void setException(Throwable cause);
Exception getException();
```

Notes :



## Gestion des erreurs : remédiable ?

- Quand une erreur remédiable remonte, 3 techniques permettent de gérer ces exceptions:
  - DoTry-doCatch : s'applique à une route, correspond au try catch java
  - OnException : peut aussi s'appliquer à un routeBuilder
  - ErrorHandler : peut aussi s'appliquer à l'ensemble du contexte Camel

- Le scope de la gestion d'erreur peut donc être :
  - La route, le routeBuilder (dsl java), contexte Camel (dsl xml)

- Camel tentera d'utiliser la gestion la plus « proche »

Notes :



## Gestion des erreurs : doTry

- Semblable au Java :
- + : Facile à comprendre des devs Java
- - : syntaxe peu lisible au final

```java
from("jms:queue.in")
  .doTry()
    .process(new MonProcessorException())
    .to("jms:queue.ok")
  .doCatch(IOException.class, BusinessException.class)
    .to("jms:queue.catch")
  .doFinally()
    .to("jms:queue.finally")
  .end();
```

Notes :



## Gestion des erreurs : doTry

- Semblable au Java :
- + : Facile à comprendre des devs Java
- - : syntaxe peu lisible au final

```java
from("jms:queue.in")
  .doTry()
    .process(new MonProcessorException())
    .to("jms:queue.ok")
  .doCatch(IOException.class, BusinessException.class)
    .to("jms:queue.catch")
  .doFinally()
    .to("jms:queue.finally")
  .end();
```

Notes :



## Gestion des erreurs : onException

- Plus spécifique et plus simple :
  - Route vers ce bloc en cas d'exception du type spécifié
  - Similaire à un `from()`
  - Le type le plus proche de l'exception dans l'arbre d'héritage est choisi si plusieurs cas s'appliquent

- La fonction handled(true) permet d'étouffer l'exception

```java
onException(Exception.class)
```

```java
onException(NoCashException.class)
  .handled(true)
  .transform().constant("Désolé, achat annulé !");
```

Notes :



## Gestion des erreurs : ErrorHandler

- Définition du handler où sont dirigées les erreurs :
- Également paramétrable via ExceptionPolicyStrategy
  - Définition des ExceptionType
  - Détermine si l'exception correspond aux critères de cet error handler

- En plus du default, d'autres handlers sont disponibles :
  - LoggingErrorHandler
  - Dead Letter Channel
  - `NoErrorHandler`
  - `TransactionErrorHandler`

```java
errorHandler(ErrorHandlerBuilder)
```

Notes :



## Gestion des erreurs : ErrorHandler

- `DefaultErrorHandler`
  - Gestionnaire d'erreur par défaut
  - Pas de redelivery ni de dead letter queue
  - Si l'échange échoue, une exception est retournée au client (`RuntimeCamelException`)
  - Correspond au modèle Java

- `LoggingErrorHandler`
  - Se contente de logger les erreurs

- `NoErrorHandler`
  - Désactivation du ErrorHandler

Notes :



## Gestion des erreurs : ErrorHandler

- `DeadLetterChannel`
  - Paramétrable via une RedeliveryPolicy
  - Transfère le message vers un channel deadletter
  - Tentatives maximum de délivrance (6 par défaut)
  - Délai entre les différentes tentatives (1 seconde)
  - Coefficient multiplicateur du délai entre tentatives (0x)

![](ressources/images/camel_07_environnement_robuste_et_ha-10000200000001C7000000FF92245631.gif)

Notes :



## Gestion des erreurs : ErrorHandler

- `CustomErrorHandler`
  - Implémentation de l'interface ErrorHandler
  - Il suffit d'implémenter la méthode process
  - Permet de créer un Handler maison pour notifier des échecs
  - Notification mail ?
  - Notification snmp ?
  - Envoi vers un aggrégateur d'évènement ?

Notes :



## Gestion des erreurs : ErrorHandler

- On peut modifier le comportement par défaut de tous les ErrorHandlers :
  - DeadLetterChannel pointant sur le Endpoint (jms, file, etc.)
  - Avec 10 tentatives maximum
  - Coefficient de multiplication de 2
  - Et un délai initial entre les tentatives de 3 secondes

```java
errorHandler(
  deadLetterChannel(Endpoint)
    .maximumRedeliveries(10)
    .backOffMultiplier(2)
    .redeliveryDelay(3000)
)
```

Notes :



## TP 9a

<!-- .slide: class="page-tp9a" -->



## Déployer un environnement de qualité, robuste et fiable :

Gestion des transactions

Notes :



## Gestion des transactions

- Les transactions peuvent être un élément fondamental dans la création d'un flux de messages
- Comment assurer l'intégrité de mes processus métiers ?
- Principe ACID
  - Atomicité
    - La transaction exécute toutes les tâches ou aucune (tout est validé, ou tout est annulé)
    - Si un échange avec 1 seule ressource a échouée, tous les échanges doivent être annulés

  - Consistance
    - Après la validation, les ressources doivent être dans un état stable

Notes :



## Gestion des transactions

- Principe ACID
  - Isolation
    - Deux transactions travaillant sur une même ressource sont totalement décorrélées
    - Aucune modification d'état n'est visible avant la validation finale

  - Durabilité
    - Une transaction validée ne peut être défaite avec une annulation
    - Il faut une transaction inversée pour revenir à l'état initial

Notes :



## Gestion des transactions

- Plusieurs types de transactions
  - Ressource unique (Single transaction)
    - On accède à une seule ressource (ex : JDBC)
    - On souhaite néanmoins grouper plusieurs opérations entres-elles pour assurer l'intégrité

  - Ressources multiples (XA transaction)
    - On accède à des ressources différentes (ex : JMS & JDBC)
    - Toutes les sous-transactions doivent être validées ou annulées simultanément

Notes :



## Gestion des transactions Camel

- Camel supporte les transactions

- Seul certains composants peuvent prendre part à une transaction
- Il faut pour cela que la ressource accédée supporte les principes transactionnels
  - HTTP ne supporte pas de transaction
  - File ne supporte pas de transaction
  - JMS supporte les transactions
  - JDBC supporte les transactions

Notes :



## Gestion des transactions Camel

- La gestion des transactions dans Camel s'appuie sur Spring
- Les endpoints qui supportent les transactions (JMS, JDBC) participeront à la transaction courante
- Politiques de transactions possibles
  - `PROPAGATION_REQUIRED`
    - Création de Tx si manquante

  - `PROPAGATION_REQUIRES_NEW`
    - Clos la Tx et en créé une nouvelle

Notes :



## Gestion des transactions Camel

- Définition des ressources transactionnelles en Spring

```xml
<bean id="jmsConnectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
  <property name="brokerURL" value="vm://localhost"/>
</bean>

<!-- Spring JMS TX Manager -->
<bean id="jmsTransactionManager" class="org.springframework.jms.connection.JmsTransactionManager">
  <property name="connectionFactory" ref="jmsConnectionFactory"/>
</bean>

<!-- Composant Camel -->
<bean id="activemq" class="org.apache.activemq.camel.component.ActiveMQComponent">
  <property name="connectionFactory" ref="jmsConnectionFactory"/>
  <property name="transacted" value="true"/>
  <property name="transactionManager" ref="jmsTransactionManager"/>
</bean>

<bean id="PROPAGATION_REQUIRED" class="org.apache.camel.spring.spi.SpringTransactionPolicy">
  <property name="transactionManager" ref="jmsTransactionManager"/>
  <property name="propagationBehaviorName" value="PROPAGATION_REQUIRED"/>
</bean>

<bean id="PROPAGATION_REQUIRES_NEW" class="org.apache.camel.spring.spi.SpringTransactionPolicy">
  <property name="transactionManager" ref="jmsTransactionManager"/>
  <property name="propagationBehaviorName" value="PROPAGATION_REQUIRES_NEW"/>
</bean>
```

Notes :



## Gestion des transactions Camel

- Configuration des routes en Camel
- Par défaut, si on ne spécifie pas la Policy, Camel choisit PROPAGATION_REQUIRED

```java
from("activemq:queue:inTX")
  .transacted("PROPAGATION_REQUIRED")
  // Process
  .to("activemq:queue:outTX");
```

Notes :



## Gestion des erreurs

- La gestion d'erreur dans un monde transactionnel est totalement différente
  - Autorise les redelivery de messages
  - Utilisation des DLQ ActiveMQ
  - Le TransactionManager aura toujours le dernier mot
    - Commit / Rollback sur la ressource

- Utilisation du TransactionErrorHandler

```java
errorHandler(transactionErrorHandler().maximumRedeliveries(10));
```

Notes :



## Gestion des erreurs

- La gestion d'erreur dans un monde transactionnel est totalement différente
  - Autorise les redelivery de messages
  - Utilisation des DLQ ActiveMQ
  - Le TransactionManager aura toujours le dernier mot
    - Commit / Rollback sur la ressource

- Utilisation du TransactionErrorHandler

```java
errorHandler(transactionErrorHandler().maximumRedeliveries(10));
```

Notes :



## Gestion des erreurs

- /!\ Certains EIP coupent les transactions par défaut :
  - Split
  - Multicast
  - RecipientList
  - Aggregate

- Pour éviter cela on peut utiliser la fonction shareUnitOfWork

```java
from(direct:maRoute)
  .transacted()
  .routeId("MA_ROUTE")
  .split(body()).shareUnitOfWork()
```

Notes :



## Gestion des erreurs

- Unit Of Work ?An object representing the unit of work processing an Exchange which allows the use of Synchronization hooks. This object might map one-to-one with a transaction in JPA or Spring; or might not.

- Synchonization ? Provides a hook for custom Processor or Component instances to respond to completed or failed processing of an Exchange => Callback

- Utilisé pour clôturer les transactions (commit/rollback)
- Mais aussi pour créer des mécanismes de compensation

```java
void addSynchronization(Synchronization synchronization);
void removeSynchronization(Synchronization synchronization);
void done(Exchange exchange);
```

```java
void onComplete(Exchange exchange);
void onFailure(Exchange exchange);
```

Notes :



## Gestion des erreurs

- Compensation avec UnitOfWork (cas non transactionnel)

```java
from("direct:confirm")
  .process(new Processor() {
      public void process(Exchange exchange) throws Exception {
        exchange.getUnitOfWork().addSynchronization(new FtpRollback());
      }
    }
  )
```

```java
public class FtpRollback implements Synchronization {
  public void onComplete(Exchange exchange) {

  }
  public void onFailure(Exchange exchange) {
  //Backup du fichier sur le serveur ?
  // Envoi d'un mail ?
  }
}
```

Notes :



## Gestion des erreurs

- Camel offre une syntaxe plus simple qui masque le UnitOfWork

```java
onCompletion()
  .log("Success");

onCompletion()
  // Opération de fait le rollback
  .onFailureOnly();

from("direct:confirm")
  // Traitement
  end();
```

Notes :



## Déployer un environnement de qualité, robuste et fiable :

Sécuriser les composants

Notes :



## Sécuriser les endpoints

- Sécurisation d'un endpoint HTTP
  - Méthode simple – authentification Basic HTTP
  - Consumer camel-jetty

```xml
<bean id="securityHandler" class="org.eclipse.jetty.security.ConstraintSecurityHandler">
  <property name="authenticator">
    <bean class="org.eclipse.jetty.security.authentication.BasicAuthenticator"/>
  </property>
  <property name="constraintMappings">
    <list>
      <ref bean="constraintMapping"/>
    </list>
  </property>
</bean>
```

```java
from("jetty:http://0.0.0.0:9080/myservice?handlers=securityHandler")
```

Notes :



## Sécuriser les endpoints

- Sécurisation d'un endpoint HTTP
  - Méthode simple – authentification Basic HTTP
  - Producer camel-http

```java
to("http://localhost:7002/httpClient?authMethod=Basic&authUsername=zenika&authPassword=é2ùdS6")
```

Notes :



## Sécuriser les endpoints

- Sécurisation d'un endpoint HTTP
  - Méthode simple – authentification Basic HTTP
  - Consumer camel-jetty

```xml
<bean id="securityHandler" class="org.eclipse.jetty.security.ConstraintSecurityHandler">
  <property name="authenticator">
    <bean class="org.eclipse.jetty.security.authentication.BasicAuthenticator"/>
  </property>
  <property name="constraintMappings">
    <list>
      <ref bean="constraintMapping"/>
    </list>
  </property>
</bean>
```
```java
from("jetty:http://0.0.0.0:9080/myservice?handlers=securityHandler")
```

Notes :



## Sécuriser les endpoints

- Sécurisation d'un endpoint HTTP
  - Méthode d'authentification forte – SSL
  - Consumer camel-jetty

```xml
<bean id="jetty" class="org.apache.camel.component.jetty.JettyHttpComponent">
  <property name="sslSocketConnectorProperties">
    <properties>
      <property name="password" value="..."/>
      <property name="keyPassword" value="..."/>
      <property name="keystore" value="..."/>
      <property name="needClientAuth" value="..."/>
      <property name="truststore" value="..."/>
    </properties>
  </property>
</bean>
```

```xml
<to uri="jetty:https://myhostname.com:443/myURL?httpClientConfigurerRef=myHttpClientConfigurer"/>
```

Notes :



## Sécuriser les endpoints

- Sécurisation d'un endpoint HTTP
  - Méthode d'authentification forte – SSL
  - Producer camel-http

```xml
<bean id="myHttpClientConfigurer" class="my.https.HttpClientConfigurer"></bean>
```

```xml
<to uri="https://myhostname.com:443/myURL?httpClientConfigurerRef=myHttpClientConfigurer"/>
```

Notes :



## Sécuriser les endpoints

- Sécurisation d'un endpoint
  - CXF supporte SSL
  - CXF supporte WS-Security
  - La majorité des endpoints courants supportent l'authentification

- Sécurisation des données des messages
  - Avec le marshalling, on peut crypter spécifiquement tous les messages
  - Avec Camel, support du XMLSecurityDataFormat qui permet de crypter très simplement les données

Notes :



## Déployer un environnement de qualité, robuste et fiable :
Clustering et haute disponibilité
Notes :



## Le clustering et la haute disponibilité

- Les flux inter-applicatifs sont au cœur du SI
- Il est indispensable que le système soit toujours joignable
  - Dans le cas contraire, les applications ne sont plus connectées, et le SI est paralysé
  - Comment faire pour qu'il soit disponible en permanence ?

- Il est également indispensable que le système puisse supporter la charge imposée par les communications au travers du SI
  - Dimensionnement adéquat du système
  - Études préalables de détermination du SLA

Notes :



## Le clustering et la haute disponibilité

- La haute disponibilité est mesurée en % d'uptime annuel

| Uptime    | Downtime autorisé |
|-----------|-------------------|
| 99%       | 88 heures         |
| 99.9%     | 9 heures          |
| 99.99%    | 53 minutes        |
| 99.999%   | 5 minutes         |
| 99.9999%  | 31 secondes       |
| 99.99999% | 3 secondes        |

![](ressources/images/camel_07_environnement_robuste_et_ha-TablePreview1.svm)

Notes :



## Le clustering et la haute disponibilité

- Le clustering est une solution pour garantir de la haute disponibilité (High Availability)
- Un cluster peut être matériel ou logiciel
- Un cluster est une grappe de serveurs ou d'applications qui sont reliés entre eux
- Le clustering répond à plusieurs problématiques
  - Le failover (tolérance aux pannes)
    - Si un serveur ou une application tombe, les autres sont là pour prendre le relai

  - La montée en charge
    - Capacité du système à absorber la charge

Notes :



## Le clustering et la haute disponibilité

- Camel n'empêche en rien de déployer plusieurs fois la même route.
- La fiabilité du fonctionnement de Camel est fourni par des éléments externes et sécurisés
  - Base de données
  - Broker de messages

- Ces éléments peuvent permettent à plusieurs routes distribuées de communiquer entre eux.

Notes :



## Une question d'architecture

- Pour les routes qui attendent les réceptions de messages (HTTP, WS, etc)
  - Un simple Load-Balancer en Round-Robin

Route A

![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)

Route A

![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)

Client

![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)

Notes :



## Une question d'architecture

- Pour les routes qui récupèrent les messages (file, jdbc, jms)
  - Poller
  - Problème d'accès concurrent aux ressources

Route A

![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)

Route A

![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)

Client

![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)
![](ressources/images/camel_07_environnement_robuste_et_ha-10000201000000400000004053EF7B88.png)

Notes :



## Eviter la double consommation

- Il existe un EIP pour ça : IdempotentRepository
- Il s'agit de faire partager aux routes un élément commun qui trace les messages reçus
- Plusieurs implémentations : File, JDBC, JMS, Hazelcast

```java
from("direct:in")
  .idempotentConsumer(header("messageId"), idempotentRepo)
  .to("direct:out");
```

Notes :



<!-- .slide: class="page-questions" -->



## TP 9b

<!-- .slide: class="page-tp9" -->