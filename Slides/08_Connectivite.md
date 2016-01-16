# Connectivité

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
- **[08 – Connectivité](#/8)**
- [09 – Routage, transformations et intégration](#/9)
- [10 – Web Services](#/10)
- [11 – Déployer un environnement de qualité, robuste et fiable](#/11)
- [12 – Surveiller l'ESB : Monitoring métier et technique](#/12)


## Les composants de Camel

- Plus d'une centaine de composants
  - Fichiers : File, FTP, SFTP, HDFS
  - Bases de données : JDBC/SQL, LDAP, MongoDB, JPA, MyBatis
  - RPC : CXF, CXFRS, RMI, HTTP, Servlet
  - Messaging : ActiveMQ/JMS, AMQP, POP3/IMAP/SMTP, XMPP
  - Bas niveau : Netty, Mina
  - Progiciels : JIRA, Salesforce
  - Réseaux sociaux : Twitter, Facebook, Linkedin
  - Etc.
- La liste mise à jour régulièrement

http://camel.apache.org/components.html



## Les composants de Camel

- Camel a 2 types de composants :
  - Logiques :
    - Simple liaison en mémoire
  - Physiques :
    - Fait intervenir le réseau et un protocole spécifique
    - Communication avec l'extérieur



## Camel – Le composant File

- Syntaxe `file:directoryName[?options]`
- Quelques options couramment utilisées
  - `autoCreate=true`
  - `delay`
  - `recursive`
  - `filter`
  - `move`

```java
from("file://order/input?move=.ok&delay=3000")
  .to("activemq:orders");
```



<!-- .slide: data-background="reveal/theme-zenika/images/tp1.png" -->
<!-- .slide: data-background-size="30%" -->



## JMS

![Schéma JMS](ressources/images/02_jms.png)




## Camel – Le composant JMS

- Syntaxe

`jms:[queue:|topic:]destinationName[?options]`

- Quelques options couramment utilisées
  - `replyTo`
  - `priority`
  - `selector`
  - `maxConcurrentConsumers`

```java
from("jms:queue.in").to("jms:queue.out");
```



## Camel – Le composant JMS

- Configurer le composant JMS

```xml
<camelContext id="camel"
              xmlns="http://camel.apache.org/schema/spring" />

<bean id="monJMS"
      class="org.apache.camel.component.jms.JmsComponent">
  <property name="connectionFactory">
    <bean class="org.apache.activemq.ActiveMQConnectionFactory">
      <property name="brokerURL" value="vm://bkr"/>
    </bean>
</property>
</bean>
```

- Et utiliser le composant configuré

`monJMS:[queue:|topic:]destination`

<!-- .element class="alert alert-danger"-->
Pour Apache ActiveMQ, utiliser le composant standard fourni avec la distribution activemq




<!-- .slide: class="page-questions" -->



## TP 4 

<!-- .slide: class="page-tp4" -->



## Camel – Le composant camel-cxf

- Le composant `camel-cxf`
  Sera introduit au chapitre sur les Webservices
- [Chapitre 10](#/10)



## Camel – Le composant camel-mail

- Le composant `camel-mail`
  - Utilisé pour interagir avec des applications manipulant des messages transitant sur les protocoles mail (imap, pop3)
  - Fonctionnalités en mode consumer
    - scrutation d'une mailbox
    - gestion de la sécurité (pop3s, imaps)
    - périodicité
    - récupération par paquet (fetchSize)
    - récupération des nouveaux mails seulement
    - suppression des mails récupérés



## Camel – Le composant camel-mail

- Le composant `camel-mail`
  - Fonctionnalités en mode producer
    - gestion de la sécurité (pop3s, imaps)
    - adresse d'envoi et réception
- Chaînes de connexion à utiliser

- `smtp://[username@]host[:port][?options]`
- `pop3://[username@]host[:port][?options]`
- `imap://[username@]host[:port][?options]`

Ou

- `smtp://host[:port]?password=somepwd&username=someuser`

Exemple :

- `imap://rde@zenika.com:143/INBOX?password=mypass`
- `pop3://pop3server/INBOX?username=rde@zenika.com&password=mypass`



## Camel – Le composant camel-jetty

- Le composant `camel-jetty`
  - Fonctionnalités en mode consumer
  - Réception d'une trame HTTP
  - Expose un service HTTP aux services externes
  - Retourne une réponse HTTP
  - Implémentation basé sur Jetty 6
  - Authentification BASIC HTTP
  - Support de SSL

`jetty:http://hostname[:port][/resourceUri][?options]`

Exemple:

- `jetty:https://0.0.0.0/myapp/myservice/`




## Camel – Le composant camel-http

- Le composant camel-http ou camel-http4
  - Basé sur Apache HttpClient
  - Fonctionnalités en mode producer
    - Émission d'une trame HTTP vers un service distant
    - Configuration de la connexion (SSL, Proxy, timeout)
    - Configuration des headers HTTP
    - Configuration des opérations (POST, GET, …)

- `http:hostname[:port][/resourceUri][?param1=value1][&param2=value2]`
- `http4:hostname[:port][/resourceUri][?options]`

Exemple :

- `http://www.zenika.com?proxyHost=proxy.zenika.com&proxyPort=80`




## Camel – Le composant bean

- Le composant Bean est une interface avec Spring
- On peut préciser le nom de la méthode à appeler
- Dispose d'un algorithme de choix de méthode « magique »
- Permet de se découpler de l'API Camel
- Puissance et flexibilité des @nnotations

```java
.to("bean:beanId")
```

ou

```java
.bean(Object)
```

ou

```java
.beanRef("beanId")
```



## Camel – Le composant bean

- Annotations sur les paramètres
  - `@Body`
  - `@Header("monHeader")`
  - `@Headers` `Map<String,Object>`
  - `@Attachments` `Map<String,Object>`
  - `@Simple`, `@XPath`, `@EL`
  - `@OutHeaders` `Map<String,Object>`



## Camel – Le composant bean

- Choix de la méthode à appeler
  1. Header : `CamelBeanMethodName`
  2. URI param : `bean:monBean?method=maMethod`
  3. Unique méthode ou unique méthode annotée `@Handler`
  4. Méthode dont le type de l'attribut `body` = type du body dans le message

- Bonne pratique : spécifier le nom de la méthode



<!-- .slide: class="page-questions" -->



## TP5

<!-- .slide: class="page-tp5" -->


## Implementer ses propres composants

- Implémenter un composant spécifique :
  - Développement Java
    - Component + Endpoint + Producer (optionnel) + Consumer (optionnel)
  - Archetype Maven pour démarrer rapidement
  - Nombreux exemples
- Fourni par le framework :
  - Intégration naturelle dans les routes
  - Configuration par URI
  - Monitoring et cycle de vie
  - Gestion des threads, polling,



## Implementer ses propres composants

TODO montrer un exemple et expliquer son fonctionnement



<!-- .slide: class="page-questions" -->


## TP6 : Facultatif

TODO créer le TP

<!-- .slide: class="page-tp6" -->