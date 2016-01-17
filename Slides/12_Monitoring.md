# Surveiller l'ESB : Monitoring métier et technique

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
- [11 – Déployer un environnement de qualité, robuste et fiable](#/11)
- **[12 – Surveiller l'ESB : Monitoring métier et technique](#/12)**



## Le monitoring dans un contexte distribué

- Les patterns de supervision système – wire tap, message store
- Administrer Camel avec JMX

Contenu

Notes :



## Monitorer une application est complexe

- Goulet d'étranglement
- Erreurs
- Arrêts

- Monitorer une application distribuée accroît la complexité de cette tâche
  - « Architect's dream, Developer's nightmare » - M. Fowler
  - Un producteur ne peut savoir qui sont les consommateurs → « Prévoir l'imprévisible »
  - Asynchronisme

- Nécessité de mettre en place des systèmes de monitoring et d'analyse adaptés aux systèmes distribués

Le monitoring d'un système distribué

Notes :



## Le bus de contrôle

![](ressources/images/camel_08_monitoring-1000020100000080000000806173FCBC.png)

Bus de contrôle

Notes :



## Fonctionnalités d'un bus de contrôle

- Configuration : chaque composant peut être configuré (si possible à la volée) depuis le bus de contrôle
- « Heartbeat » : chaque composant devrait envoyer un message périodique indiquant qu'il fonctionne correctement
- Messages de test : chaque composant offre la possibilité de prendre en charge des messages de test afin de vérifier qu'il traite correctement les informations envoyées

Notes :



## EIP : Message de test

![](ressources/images/camel_08_monitoring-100002010000008000000080C5CF4130.png)

Notes :



## Fonctionnalités d'un bus de contrôle

- Exceptions : L'ensemble des erreurs doivent être renvoyées au bus de contrôle afin de pouvoir régler d'éventuels problèmes
- Statistiques : Afin de vérifier le bon fonctionnement du système, il est nécessaire de collecter des statistiques (débit, temps de traitement …)
- Console : Une console centrale permet de contrôler les fonctionnalités décrites

Notes :



## JMX : le cœur de bus de contrôle

- Camel expose ses composants internes via JMX
- JMX permet d'accéder aux informations de(s)
  - Endpoints
  - Contexte
  - Routes

- JMX permet de démarrer/arrêter des routes Camel
- JMX permet d'accéder aux statistiques de Camel
- Il est possible d'accéder à l'ensemble de ces informations depuis un client Java (exemple : JConsole) ou depuis du code Java

Notes :



## Composant ControlBus

- Action : start, stop, status, suspend, resume, stats

```java
from("direct:startMaRoute")
  .to("controlbus:route?routeId=maRoute&action=start")
```

Notes :



## Exemple de client JMX Camel

```java
…
JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");

Map<String, Object> environment = new HashMap<String, Object>();
String[] credentials = new String[] { "login", "password" };
environment.put(JMXConnector.CREDENTIALS, credentials);

jmxc = JMXConnectorFactory.connect(url, environment);

MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
ObjectName mbeanName = new ObjectName("org.apache.servicemix:ContainerName=ServiceMix,Type=Statistics,SubType=Endpoint,Name=internal_{http://www.zenika.com}xslt_endpoint");

System.out.println("ECHANGE ENTREE : " + mbsc.getAttribute(mbeanName, "inboundExchangeCount"));
…
```

Notes :



## Camel et le monitoring

- Camel ne fournit pas d'outil de monitoring de haut niveau
- On peut s'appuyer sur JMX pour mettre en place un bus de contrôle au sein de Camel
  - Configuration
  - Statistiques

- Afin d'apporter plus de richesse au monitoring, on peut s'appuyer sur les EIP dédiés

Notes :



## EIP : Wire Tap

- En Camel

```java
from("direct:source")
  .to("log:serviceLogger")
  .wireTap("direct:audit")
  .to("direct:destination");
```

![](ressources/images/camel_08_monitoring-10000200000001FE000000B589476ACE.gif)

Permet d'envoyer une copie d'un message vers le système de monitoring ou vers un stockage de données

Notes :



## EIP : Detour

- En Camel

Booléen

```java
from("direct:source")
  .choice()
    .when().method("beanControle", "detour")
      .to("direct:detour")
    .otherwise()
      .to("direct:destination");
  .end()
```

![](ressources/images/camel_08_monitoring-100000000000025E000000B8A44CF116.gif)

Permet de détourner des messages en cas de besoin (validation, débogage)

Notes :



## Logs

- Basé sur SLF4J
- Camel Context
  - Tracer Interceptor (Debug uniquement)
  - MDC Logging : exchangeId, messageId, correlationId

- CXF Logging Interceptor

```xml
<camelContext id="monCamel" trace="true" useMDCLogging="true">
```

```xml
<cxf:bus xmlns="http://cxf.apache.org/core">
  <cxf:features>
    <cxf:logging/>
  </cxf:features>
</cxf:bus>
```

Notes :



## Logs

- Composant Log
- EIP Log
- Bonnes pratiques
  - Utiliser le CamelCorrelationId et le Thread name
  - Centraliser les logs : ELK, GrayLog2, Splunk...

```java
from("direct:source")
  .log(LoggingLevel.INFO,
    "com.zenika.MaRoute",
    "Traitement fichier ${header.CamelFileName} ${body}")
  .to("direct:destination");
```

```java
from("direct:source")
  .to("log:com.zenika.MaRoute?level=DEBUG&showAll=true&multiline=true")
  .to("direct:destination");
```

Notes :



## Monitoring en bref

- Il est important de penser aux problématiques de monitoring dès le début d'un chantier d'intégration
- Les solutions de monitoring peuvent aller
  - du plus simple
    - Fichiers de log
    - Base de données

  - Au plus complexe
    - BAM (Business Activity Monitoring)
    - CEP (Complex Event Processing)

- D'autres EIP de monitoring sont disponibles
  - Message History
  - Smart Proxy

Notes :



<!-- .slide: class="page-questions" -->



## TP 10

<!-- .slide: class="page-tp10" -->