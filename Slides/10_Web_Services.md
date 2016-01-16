# Web Services

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
- **[10 – Web Services](#/10)**
- [11 – Déployer un environnement de qualité, robuste et fiable](#/11)
- [12 – Surveiller l'ESB : Monitoring métier et technique](#/12)



## Introduction aux Web Services

- Service Web SOAP
- Service Web REST
- Gestion des versions de Web Services
- Orchestration simple de Web Services

Contenu

Notes :



## Un Web Service est un module logiciel autonome

- Un Web Service fournit une description de ses capacités
- Un Web Service est accessible depuis un réseau
  - Intranet
  - Internet

- Un Web Service communique via des protocoles standards de communication (HTTP essentiellement)

Un Web Service, c'est quoi ?

Notes :



## Web Services : WS-* et REST

- WS-*
  - Solution standard
  - RPC synchrone et/ou asynchrone (via WS-Addressing)
  - S'appuie sur SOAP (communication) & WSDL (contrat)
  - Intégration d'un écosystème orienté entreprise
    - WS-Security / WS-Addressing / Ws-ReliableMessaging ...

- REST
  - S'appuie sur les fondations HTTP
  - « Architecture Orientée Ressources »
  - Opérations de base : GET / POST / PUT / DELETE
  - Alternative sérieuse au monde WS-*
    - Simple à mettre en œuvre
    - Pas de nouvelles technologies à maîtriser

Notes :



## Les Web Services dans l'Entreprise

Système d'information

![](ressources/images/camel_05_web_services-10000201000000400000004052ECF001.png)
![](ressources/images/camel_05_web_services-10000201000000400000004089227A75.png)
![](ressources/images/camel_05_web_services-100002010000004000000040BF47BE8F.png)

Client
Filiale
Partenaire
Offre
EJB

![](ressources/images/camel_05_web_services-100002010000004000000040BED8F5F4.png)

WS

![](ressources/images/camel_05_web_services-100002010000004000000040BED8F5F4.png)

WS

![](ressources/images/camel_05_web_services-100002010000004000000040F81E0FFE.png)
![](ressources/images/camel_05_web_services-1000020100000040000000408161F7B7.png)
![](ressources/images/camel_05_web_services-1000020100000040000000408161F7B7.png)
![](ressources/images/camel_05_web_services-1000020100000040000000408161F7B7.png)

Hotline
Réservation
Facturation
WS

![](ressources/images/camel_05_web_services-1000020100000040000000408DBBE544.png)
![](ressources/images/camel_05_web_services-1000020100000040000000408161F7B7.png)

Notes :



## Web Services et Intégration

- Porte d'entrée du Système d'information
- Outil d'intégration

SI
Bus

![](ressources/images/camel_05_web_services-100002010000004000000040BF47BE8F.png)
![](ressources/images/camel_05_web_services-1000020100000040000000408161F7B7.png)

WSWS
JMSWS
XSLTWS
DB

![](ressources/images/camel_05_web_services-1000020100000040000000407E838337.png)

WS
WS
WS
EJBWS
DBWS
CRMWSWS

Notes :



## Web Services SOAP

Notes :



## Les fondations des Web Services

SOAP
Web Service
WSDL
UDDI

Lie le service àutilisé pour communiquer avecLie le service àdécritréférenceAccessible par

Notes :



## Les fondations des Web Services

- WSDL
  - Décrit le service
  - WSDL = Contrat

- SOAP
  - Langage/Protocole de communication
  - Permet la transmission de messages standardisés entre acteurs d'un système distribué

- Web Services
  - Implémentation du service
  - Indépendant des technologies : Java, .Net, Python …

- UDDI
  - Référence les services
  - « Parent pauvre » du monde WS-*

Notes :



## SOAP : Protocole de communication

- Un message SOAP
  - Est un message XML
  - Possède une structure propre (ie. enveloppe SOAP)
  - Est indépendant de la couche transport : HTTP, JMS, ...

- Un message SOAP contient les éléments
  - Envelope qui en est la racine
  - Header qui fournit des informations techniques :
    - Routage
    - adressage …

  - Body qui contient le contenu utile (ie. payload) du message :
    - Requête
    - Réponse
    - Erreur

Notes :



## L'enveloppe SOAP

![](ressources/images/camel_05_web_services-10000201000000050000024B322F88B6.png)

```xml
<soapenv:Envelope...>

  <soapenv:Header>
    <add:MessageID>132465789</add:MessageID>
  </soapenv:Header>

  <soapenv:Body>
    <ns:rechercherVoyage>
      <ns:depart>Paris</ns:depart>
      <ns:arrivee>Rennes</ns:arrivee>
      <ns:date>2009-12-31</date>
    </ns:rechercherVoyage>
  </soapenv:Body>

</soapenv:Envelope>
```

Enveloppe
Entête
Corps

Notes :



## Structure d'un WSDL

Interface(définition abstraite)Implémentation(définition concrète)

```xml
<definitions><types>…</types><message>…</message><portType><operation>…</operation> </portType>
```

```xml
<binding>…</binding><service>…</service></definitions>
```

Notes :



## WSDL : définition abstraite/concrète

- Définition abstraite (ou logique)
  - Contenu des messages (entrée/sortie)
  - Opérations possibles
    - Paramètres
    - Erreurs

- Définition concrète (ou physique)
  - Implémentation du service
    - Format des messages
    - Type de communication

  - Emplacement du service
    - Adresse réseau

Notes :



## L'offre Java / Web Services

- JAX-WS :
  - Standard Java Web Services, inclus à Java SE
  - Annotations : `@WebService`, `@WebParam` …
  - Basé sur JAXB pour le binding XML
  - Plusieurs implémentations de : CXF, Metro, Axis 2…
  - Permet de :
    - Créer des Web Services
    - Créer des clients Web (proxy)
    - Publier des Web Services

- Spring WS
  - Annotations : `@Endpoint`, `@RequestPayload`...
  - Permet de :
    - Créer des Web Services
    - Créer des clients Web (WebServiceTemplate)

Notes :



## Comment développer un WS en Java ?

- Contract-First (ou Top-Down)

  - Création du fichier WSDL et du schéma XSD
  - Génération automatique des classes Java

  - Classes JAXB (requête/réponse/erreurs)
  - Interface du service

  - Implémentation du service

- Code-First (ou Bottom-Up)

  - Implémentation du service
  - Génération automatique du fichier WSDL et du schéma XSD

Notes :



## Comparaison Code-First/Contract-First

- Code-First
  - Est facile et rapide à mettre en œuvre pour des cas simples (ie. XML basique)
  - Devient plus complexe et long à mettre en œuvre sur des services manipulant des données structurées
  - contrat (ie. WSDL) difficile à maîtriser → peut poser des problèmes d'interopérabilité

- Contract-First
  - Nécessite de maîtriser WSDL et XSD
  - Plus long à mettre en œuvre
  - Garantit l'interopérabilité du service

Il est fortement conseillé de développer en Contract-First

![](ressources/images/camel_05_web_services-100002010000004000000040B01A4293.png)

Notes :



## XSD, XML, JAXB

XSD
XML
Classe
Instance
compilateurmarshal
unmarshalrespecteinstanceJDK: xjc, schemagen

Notes :



## WSDL, SOAP, JAXWS

WSDL
Message
SOAP
Interface
Java
Implém.
Java Serveur
compilateurproduce
consumerespecteinstanceJDK: wsimport, wsgen
CXF: wsdl2java
Proxy
Java Cliente

Notes :



## Génération Java à partir du WSDL

- CXF fournit l'outil wsdl2java afin de générer les classes Java à partir d'un fichier WSDL
- Exemple
- L'outil wsdl2java génère
  - L'ensemble des classes JAXB
  - L'interface du service (nom de le classe = nom du portType)
  - Une implémentation du service à compléter (nom de le classe = nom du portType + « Impl »)
  - Une classe cliente (nom de le classe = nom du service)

```shell
wsdl2java -p com.resanet.ws -impl voyages.wsdl
```
Nom du package des classes Implémentation du service

Notes :



## Service Endpoint Interface : SEI

- SEI = Interface Java + annotation @WebService
- Interface définissant les opérations du service
- Le SEI est l'équivalent Java du portType WSDL
- Le SEI n'est pas obligatoire mais est fortement conseillé
  - Approche « contrat » : séparation du contrat et de l'implémentation
    - Maintenance
    - Réutilisation
    - Tests

  - Développement client WS simplifié

Notes :



## SEI : exemple

… @WebService(targetNamespace = "http://ws.resanet.com", name = "voyagePortType")@XmlSeeAlso({ObjectFactory.class})public interface VoyagePortType {@RequestWrapper(localName = "estDisponible", targetNamespace = "http://ws.resanet.com", className = "com.resanet.ws.EstDisponible")@ResponseWrapper(localName = "estDisponibleResponse", targetNamespace = "http://ws.resanet.com", className = "com.resanet.ws.EstDisponibleResponse")@WebResult(name = "disponible", targetNamespace = "http://ws.resanet.com")@WebMethodpublic boolean estDisponible(@WebParam(name = "ref", targetNamespace = "http://ws.resanet.com")String ref);}

Notes :



## SEI : les annotations

|Annotation|Description|
|---|---|
|@WebService|Indique que l'interface correspond à Web Service.

- targetNamespace : namespace du portType
- name : nom du portType
||@XmlSeeAlso|Factory JAXB|
|@RequestWrapper|Classe JAXB représentant la requête.

- targetNamespace : namespace du portType
- localName : nom de l'élément XML
- class : classe JAXB
||@ResponseWrapper|Classe JAXB représentant la réponse.

- targetNamespace : namespace du portType
- localName : nom de l'élément XML
- class : classe JAXB
|

![](ressources/images/camel_05_web_services-TablePreview1.svm)

Notes :



## SEI : les annotations

|Annotation|Description|
|---|---|
|@WebMethod|Opération du Web Service.|
|@WebParam|Paramètre d'entrée de l'opération.

- targetNamespace : namespace de l'élement correspondant dans le wrapper (cf. RequestWrapper)
- nom : nom de l'élément correspondant dans l'élément
||@WebResult|Paramètre de sortie de l'opération.

- targetNamespace : namespace de l'élement correspondant dans le wrapper (cf. ResponseWrapper)
- nom : nom de l'élément correspondant dans l'élément
|

![](ressources/images/camel_05_web_services-TablePreview2.svm)
![](ressources/images/camel_05_web_services-1000020100000080000000802E71420D.png)

- Il est nécessaire de regénérer le SEI si modification du portType ou du schéma XSD
- Il est important de ne pas modifier les annotations afin d'éviter des incohérences Java/WSDL → rupture du contrat

Notes :



## Implémentation du service

- L'implémentation du service est un POJO annoté avec @WebService (ie. comme le SEI)
- Il est possible de créer le squelette de l'implémentation via le paramètre -impl de wsdl2java
- Exemple

```java
  …
  @WebService(
    targetNamespace = "http://ws.resanet.com",
    serviceName = "voyageService",
    portName = "voyageServicePort",
    wsdlLocation = "classpath:wsdl/voyages.wsdl",
    endpointInterface = "com.resanet.ws.VoyagePortType")
  public class VoyagePortTypeImpl implements VoyagePortType {
    public boolean estDisponible() {
      …
    }
    …
  }
```

Namespace du service
Nom du service
Nom du port
WSDL
SEI

Notes :



## Camel et Web Services

- Camel CXF permet de
  - Produire des WS SOAP (serveur, producer)
    - Transports : Jetty, Servlet

  - Consommer des WS SOAP (client, consumer)

- Types de payload (dataFormat)
  - MESSAGE : XML avec enveloppe SOAP
  - PAYLOAD : XML, body SOAP seul
  - POJO : body sous forme de POJO JAXB

Notes :



## Camel-cxf : exposer un WSDL

`cxf:bean:cxfEndpoint[?options]`
Ou
`cxf://someAddress[?options]`

Exemple:
`cxf:bean:cxfEndpoint?wsdlURL=wsdl/helloworld.wsdl&dataFormat=PAYLOAD`

```xml
<beans
  xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:cxf="http://camel.apache.org/schema/cxf">

<cxf:cxfEndpoint
  id="cxfEndpoint"
  address="http://localhost:8080/services"
  serviceClass="com.zenika.service.HelloPortType">
</cxf:cxfEndpoint>

</beans>
```

Notes :



## Camel-CXF : WS-*

- Il est possible d'utiliser aussi le composant HTTP pour interfacer un Web Service
- CXF prend en charge les spécifications WS-* compatibles
  - WS-Addressing : permet de router les messages via des informations « Header »
  - WS-Security : gère l'authentification/signature/cryptage
  - WS-ReliableMessaging : gère la fiabilité des échanges

Notes :



## TP 8a

<!-- .slide: class="page-tp8a" -->

Notes :



## Web Services REST

Notes :



## L'offre Java / Web Services

- JAX-RS :
  - Standard Java Web Services, inclus à Java SE
  - Annotations : `@Path`, `@QueryParam` …
  - Plusieurs implémentations de : CXFRS, Jersey, RESTEasy…
  - Permet de :
    - Créer des Web Services
    - Créer des clients Web (proxy ou API Client)

- Spring MVC :
  - Annotations : `@RestController`, `@RequestParam`...
  - Permet de :
    - Créer des Web Services
    - Créer des clients Web (RestTemplate)

- RESTLet :

Notes :



## Camel et Web Services REST

- Camel CXFRS permet de
  - Produire des WS REST (serveur, producer, `<rsServer>`)
    - Transports : Jetty, Servlet

  - Consommer des WS REST (client, consumer, `<rsClient>`)

- Types de payload (bindingStyle)
  - Default : POJO (comme WS SOAP)
  - SimpleConsumer : POJO malins

- Mapping Objet/JSON
  - Jettison (défaut CXF)
  - Jackson

Notes :



## Camel et Web Services REST

```xml
<bean id="jsonProvider" class="com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider"/>
<cxf:rsServer id="clientResource"address="http://localhost:9000/rest"serviceClass="com.resanet.client.ClientResource">
  <cxf:providers><ref bean="jsonProvider" /></cxf:providers>
</cxf:rsServer>
```

```java
@Path("/client")
public class ClientResource {

  @GET @Path("/{id}")
  @Produces("application/json")
  public Client getClient(@PathParam("id") long id) {
    return null;
  }
}
```

```java
from("cxfrs:bean:clientResource").to("direct:client")
```

Notes :



## Camel et Web Services REST

- Contrat/description d'interface

|                         | WADL          | Swagger              |
|-------------------------|---------------|----------------------|
|                         | Intégré à CXF | Ecrit en Scala       |
| Annotations spécifiques | @Description  | @Api, @ApiParam      |
| Format description      | XML           | JSON                 |
| Outillage               | wadl2java     | codegen jaxrs js php |
| IHM                     | SoapUI 5.0    | Swagger UI           |

![](ressources/images/camel_05_web_services-TablePreview3.svm)

Notes :



## Web Services REST : WADL

```xml
<application xmlns="http://wadl.dev.java.net/2009/02" xmlns:xs="http://www.w3.org/2001/XMLSchema">
<grammars>
  <xs:schema><!-- XSD --></xs:schema>
</grammars>
<resources base="http://localhost:9000/rest">
  <resource path="/">
    <resource path="client/{id}">
      <param name="ref" style="template" type="xs:long"/>
      <method name="GET">
        <response><representation mediaType="application/json"/></response>
      </method>
    </resource>
  </resource>
</resources>
</application>
```

Notes :



## Web Services REST : Swagger Spec

```json
{
  "apiVersion": "1.0.0",
  "swaggerVersion": "1.2",
  "basePath": "http://localhost:9000/rest/api-docs",
  "resourcePath": "/client",
  "apis": [
    {
      "path": "/client/{ref}",
      "operations": [
        {
          "method": "GET",
          "summary": "Détail Client",
          "type": "void",
          "nickname": "getClient",
          "produces": ["application/json"],
          "parameters": [
            {
              "name": "id",
              "description": "Id Client",
              "required": false,
              "type": "string",
              "paramType": "path",
              "allowMultiple": false
```

Notes :



## Versionning & Orchestration

des Web Services

Notes :



## L'évolution d'un Web Service

- Le contrat d'un Web Service est susceptible d'évoluer dans le temps
  - Évolutions sans impact (sur les consommateurs ) :
    - Ajouter une opération
    - Ajouter des nouveaux types

  - Évolutions avec impact (sur les consommateurs) :
    - Supprimer une opération
    - Renommer une opération
    - Changer les paramètres d'une opération (ordre/types)
    - Changer la structure d'un type complexe

- L'implémentation peut également évoluer - l'approche contrat « protège » le consommateur
  - QoS
  - Algorithme
  - …

Notes :



## Gestion des versions

- Afin d'éviter toute confusion et toute anomalie (coté client), il est nécessaire de mettre en place une gestion de version Web Services
- Il n'est pas forcément nécessaire de « versionner » les évolutions mineures
- Il est conseillé de s'appuyer sur les namespaces pour désigner les versions des Web Services.
  - http://www.zenika.com/2009/12/31/formations
  - http://www.zenika.com/formations/v2.1

Notes :



## Stratégies de gestion des versions WS

- Maintien de plusieurs versions du Web Service afin de laisser le temps à l'ensemble des consommateurs de migrer
- Envoi d'un message d'erreur au consommateur lui indiquant l'adresse de migration
- Redirection des requêtes « n-1 » vers la dernière version du Web Service. Cette stratégie est applicable pour :

  - Changement de type et/ou de structure
  - Changement de paramètres (ordre/liste)

- il est conseillé de mettre en place un système permettant de notifier les changements de version aux consommateurs

Notes :



## Camel : Maintien de plusieurs versions

CXF
Service 1
CXFEndpoint1

![](ressources/images/camel_05_web_services-100002010000004000000040BF47BE8F.png)
![](ressources/images/camel_05_web_services-1000020100000040000000408161F7B7.png)
![](ressources/images/camel_05_web_services-10000201000000400000004060357657.png)

CXF
Service 1
CXFEndpoint1

![](ressources/images/camel_05_web_services-100002010000004000000040BF47BE8F.png)
![](ressources/images/camel_05_web_services-1000020100000040000000408161F7B7.png)

CXF
Service 2
CXFEndpoint2

Notes :



## Camel : Envoi d'un message d'erreur

CXF
Service 1
CXFEndpoint1

![](ressources/images/camel_05_web_services-100002010000004000000040BF47BE8F.png)
![](ressources/images/camel_05_web_services-1000020100000040000000408161F7B7.png)
![](ressources/images/camel_05_web_services-10000201000000400000004060357657.png)

CXF
Service 1
CXFEndpoint1

![](ressources/images/camel_05_web_services-100002010000004000000040BF47BE8F.png)
![](ressources/images/camel_05_web_services-1000020100000040000000408161F7B7.png)

CXF
Service 2
CXFEndpoint2

CXF
Service 1'

![](ressources/images/camel_05_web_services-100002010000002000000020C475EABE.png)

Notes :



## Camel : Envoi d'un message d'erreur

CXF
Service 1
CXFEndpoint1

![](ressources/images/camel_05_web_services-100002010000004000000040BF47BE8F.png)
![](ressources/images/camel_05_web_services-1000020100000040000000408161F7B7.png)
![](ressources/images/camel_05_web_services-10000201000000400000004060357657.png)

XSLT
CXFEndpoint1

![](ressources/images/camel_05_web_services-100002010000004000000040BF47BE8F.png)
![](ressources/images/camel_05_web_services-1000020100000040000000408161F7B7.png)

CXF
Service 2
CXFEndpoint2

Notes :



## Camel et WS : Orchestration simple

- Camel peut avoir le rôle d'un orchestrateur simple de WS
- Orchestration de Web Services : organiser l'appel et la composition à des Web Services afin de mettre en œuvre un processus métier
- Camel s'appuie sur le routage et les transformations pour orchestrer des Web Services
- Si les besoins en terme d'orchestration sont complexes, il est alors conseillé de s'appuyer sur le standard BPEL
  - Langage dédié à l'orchestration des Web Services
  - Appel synchrone/asynchrone ; conditions/boucles ; gestion des erreurs ...

Notes :



## Exemple d'orchestration de Web Services

CXF
Service
CXFEndpoint

![](ressources/images/camel_05_web_services-100002010000004000000040BF47BE8F.png)
![](ressources/images/camel_05_web_services-1000020100000040000000408161F7B7.png)

CXFEndpointWeb Service

Notes :




<!-- .slide: class="page-questions" -->



## TP 8b

<!-- .slide: class="page-tp8b" -->