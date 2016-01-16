<div style="height:24.7cm; position: relative; border: 1px solid black;">
    <h1 style="position:absolute; top: 33%; width:100%; text-align: center;">{Titre-Formation}</h1>
    <h1 style="position:absolute; top: 50%; width:100%; text-align: center;">Travaux Pratiques</h1>
    <img src="ressources/logo-zenika-small.png" style="position: absolute; bottom: 20px; right: 20px; height: 150px;">
</div>
<div class="pb"></div>


## Pré-requis

### Installation

Installer les technos demandées


## TP 1 : Premier exercice

### Compiler

- item 2
- item 3
- item 4


## TP 2 : Exercice suivant

### Créer une application

- item 1
- item 2
- item 3
- item 4

### Bonus

- Ajouter une cible qui surveille le Gruntfile lui-même.


<div class="pb"></div>


## Annexes

Informations supplémentaires






## TP0. Mise en situation

### 1. Objectif

Le projet consiste à appliquer les principes ESB & SOA au SI d'une société de réservation de voyages en ligne nommée Res@Net.

Nous allons adapter progressivement au cours des TP le SI existant sous la forme d'une architecture SOA basé sur un ESB, et ensuite nous ferons évoluer ce SI au gré des  changements métiers.

### 2. Le SI existant

Le SI existant se compose de 5 applications internes
Web : interface web permettant aux clients de réserver des voyages.
Catalogue : application permettant aux responsables commerciaux de gérer les offres.
Réservation : application permettant de réserver des billets d'avion chez le partenaire AirAlpha
Comptabilité : application gérant la comptabilité de Res@net
Mail : Envoi de mails aux client

Et de 2 applications externes :
AirAlpha : Compagnie aérienne
CentralCompta : Société comptable



L'architecture actuelle de type « point à point ». Voici le détail des flux.

Flux internes
Catalogue  Web : toutes les nuits, l'ensemble du catalogue sous forme d'export XML (via un fichier) est transmis à l'application Web afin de mettre à jour sa base de données. Les 2 applications fonctionnent sur la même machine physique, le fichier catalogue est donc copié localement dans un répertoire d'import par un batch. Un second batch est chargé d'importer le contenu dans l'application web.

Web  Réservation : chaque demande de réservation, une fois la vérification contentieux effectuée, est transmise au service Réservation.

Réservation  Comptabilité : l'ensemble des transactions journalières acceptées par le partenaire est transmis chaque nuit à la comptabilité afin de calculer le chiffre d'affaires. Les transactions sont répertoriées dans un fichier CSV. Le fichier CSV est généré par un batch également chargé de l'envoi FTP. Du coté Comptabilité, un autre batch est chargé de scruter le répertoire d'import et d'enregistrer les données dans la base de données.

Réservation  Clients : périodiquement un fichier contenant les réservations closes (OK ou KO) sont transmises via FTP au service Clients qui est chargé d'envoyer les mails de confirmation/annulation aux clients (remarque : pour des raisons de sécurité, seule la machine C peut accéder au serveur mail).

Flux externes
Réservation  AirAlpha : chaque demande de réservation est transmise au partenaire AirAlpha via une file JMS. La réponse est transmise par AirAlpha via une autre file JMS (remarque : les files appartiennent au SI Res@net).

Comptabilité  CentralCompta : périodiquement l'ensemble des résultats comptables sont fournis à un prestataire extérieur (remarque : le serveur FTP est situé chez CentralCompta).

## TP1. Prise en main de ServiceMix 4 et d'ActiveMQ 5

### 1. ServiceMix 4

 a) Objectif

Prise en main du produit Apache ServiceMix 4.

 b) Pré-requis

Télécharger les binaires SMX 4.5.3
Décompresser l'archive

 c) Consignes

- Observer l'arborescence du produit
- Démarrer ServiceMix 4 en mode standalone
- Observer la complétion de la console <tab> pour afficher les commandes disponibles
- Observer les logs
- Lister les bundles OSGi
- Lister les features

### 2. ActiveMQ 5

 a) Objectif
Prise en main du produit Apache ActiveMQ 5.

 b) Pré-requis

On utilisera le broker ActiveMQ inclus dans l'ESB.
 c) Consignes

Installer la feature hawtio

features:addurl mvn:io.hawt/hawtio-karaf/1.2.0/xml/features
features:install hawtio
Se connecter sur la console d'administration (http://localhost:8181/hawtio/)
Créer une file nommée « test »
Envoyer un message quelconque sur cette file
Afficher le message


## TP2. Mise en place d'un premier flux

### 1. Objectif

Mettre en place un premier conteneur de services en faisant évoluer le flux entre l'application Web et l'application Catalogue sur un mode ESB. Cela va permettre de découvrir :
Le composant camel-file
Le déploiement à chaud d'une route Camel

### 2. Contexte

Pour rappel, la situation actuelle est la suivante :


La DSI de Res@net a décider de mettre en place un ESB entre les applications Web et Catalogue pour commencer, et la société fait appel à vous.

### 3. Mise en place

Chemin des répertoires d'export Catalogue et d'import Web

- C:\resanet\exportCatalogue
- C:\resanet\importWeb

Mettre en place une route avec 2 endpoints à l 'aide de l'API XML :

- file consumer : scrute le répertoire catalogue toutes les 10 secondes
- file provider : copie le fichier reçu dans le répertoire web

Déployer le fichier XML et vérifier le bon fonctionnement avec un test

Bonus : Développer la même route avec le DSL Java

## TP3. Connecter les applications Réservation & Comptabilité via l'ESB

### 1. Objectif

Intégrer un serveur de messages au SI via l'ESB.
Le composant camel-jms

### 2. Contexte

On souhaite que le flux Réservation → Comptabilité transite par l'ESB. Cependant, l'application Réservation sait manipuler nativement des messages JMS (alors que la génération du fichier CSV nécessite beaucoup de code spécifique). Un simple paramétrage permet à l'application Réservation d'envoyer le contenu du CSV en message JMS.
On veut donc intégrer l'application Réservation à l'ESB via l'API JMS.

On souhaite par la suite écrire sous forme de fichiers le contenu des messages JMS dans un répertoire de sortie importReservationCompta.

Le contenu des messages JMS répond au schéma XML suivant

	<?xml version="1.0" encoding="UTF-8"?>
	
	<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
	           xmlns:ns1="http://resa.resanet.com"
	           targetNamespace="http://resa.resanet.com"
	           elementFormDefault="qualified"
	           attributeFormDefault="unqualified">
	
	<xs:element name="reservation">
	    <xs:complexType>
	        <xs:sequence>
	            <xs:element name="resa" maxOccurs="unbounded">
	                <xs:complexType>
	                    <xs:sequence>
	                        <xs:element name="produit">
	                            <xs:simpleType>
	                                <xs:restriction base="xs:string">
	                                    <xs:maxLength value="3"/>
	                                </xs:restriction>
	                            </xs:simpleType>
	                        </xs:element>
	                        <xs:element name="date" type="xs:date"/>
	                        <xs:element name="quantite" type="xs:int"/>
	                    </xs:sequence>
	                </xs:complexType>
	            </xs:element>
	        </xs:sequence>
	    </xs:complexType>
	</xs:element>
	
	</xs:schema>

### 3. Mise en place

Déployer un endpoint JMS sur l'ESB pour intégrer Réservation → Comptabilité
Écrire le contenu des messages JMS dans C:\resanet\importReservationCompta
Utiliser la console HawtIO pour envoyer des messages dans la route

## TP4.  du flux entre Réservation & Comptabilité

### 1. Objectif

Découvrir et comprendre le marshalling (transformation de format).

### 2. Contexte

La société Res@net est très contente du changement de flux réalisé au TP 3.
Cependant, les fichiers générés dans le répertoire importReservationCompta sont encore en XML, et nécessite toujours d'être transformés au format CSV ( « ; » comme séparateur)
produit;date;quantite


De plus, les fichiers ont un nom étrange. La DSI souhaiterait que les fichiers répondent au pattern suivant :
transac-<yyyy-MM-DD-HH-mm-SS-sss>.csv

### 3. Mise en place

Modifier le projet mis en place au TP3 pour que le contenu des fichiers soit au format CSV attendu dans le répertoire importReservationCSVCompta
Le nom des fichiers doit répondre au pattern attendu


Note : le mapping OXM (Object XML Mapping) peut être réalisé avec JAXB (Java Architecture XML Binding)).




## TP5. Routage simple

### 1. Objectif

Prise en main du framework Camel et intégration dans ServiceMix.

### 2. Exercices
 
 a) Flow Pipe

Implémenter la route suivante avec Camel
On utilisera le composant JMS en mode consumer, la brique EIP Pipe



 b) Content Based Router
Implémenter l'EIP CBR se basant sur un header JMS nommé typeMessage
On utilisera le component Camel JMS, la brique EIP ContentBasedRouter pour transférer les messages vers 2 autres files JMS (choix basé sur le header).


 c) Multicast

Implémenter l'EIP Multicast Camel
On injectera directement un message de test dans la brique Multicast, et on affichera à l'écran les messages de sorties.
Implémenter l'EIP Multicast Camel en parallèle


 d) Message translator

Implémenter l'EIP Message Translator
On souhaite ajouter « World ! » au message d'entrée « Hello  »
On injectera directement les messages de tests dans l'implémentation de l'EIP, et on affichera à l'écran les messages de sorties.

## TP6. Routage d'un flux de messages

### 1. Objectif

Déployer des routes de messages au travers de l'ESB et des EIP implémentés par le framework Camel. Création d'un flux de messages complet.


### 2. Contexte

Implémenter les EIP et les règles de routage d'après le schéma suivant.


### 3. Mise en place

1. Deux types de messages arrivent de la file in.requete
1. requete (requete.xsd) contenant un header JMS correlation_id qui contient un identifiant de correlation unique
2. notification (notification.xsd)

2. A la brique 1, router les messages de la façon suivante
1. requete vers la brique 2
2. notification vers la file out.notification

3. A la brique 2, segmenter le message requete en 3 sous-messages
1. élément XML {http://esb.resanet.com}message1
2. élément XML {http://esb.resanet.com}message2
3. élément XML {http://esb.resanet.com}message3

4. Router chacun de ces sous-messages vers 3 objets Java implémentant l'interface suivante


	public interface Worker {
	
		/**
		 * Ajoute un header et remplace le corps du message.
		 * 
		 * @param body le corps du message
		 * @param exchange l'echange de messages
		 * 
		 * @return le nouveau corps du message
		 */
		String work(String body, org.apache.camel.Exchange exchange);
	
	}

5. Ces objets Java remplissent les fonctionnalités suivantes
1. ajoutent chacun un header facilement identifiable au message (ex : header.bean1). Ces headers contiennent une valeur de type String
2. remplacent respectivement le nom des éléments message1, message2 et message3 par bean1, bean2 et bean3 dans le corps du message

6. Les réponses de ces 3 objets Java sont envoyées à la brique n°3

7. La brique n°3 concatène les 3 messages qui ont un header correlation_id identique (celui fourni par le message requete initial)
1. le nouveau message formé est nommé reponse (reponse.xsd)
2. il contient les 3 éléments bean1, bean2 et bean3.

8. A la brique n°4, on souhaite que
1. messages reponse ayant correlation_id pair routés vers out.reponse.paire
2. messages reponse ayant correlation_id impair routés vers out.reponse.impaire
3. message reponse JMS contienne également le header JMS correlation_id, ainsi que les headers ajoutés par les objets Java
4. un seul component doit être utilisé pour l'envoi JMS


## TP7. VETRO

### 1. Objectif

Mise en place du pattern VETRO avec utilisation des composants suivants:

- Component Camel JMS
- EIP et composants Camel

### 2. Contexte

Intégration du flux Web → Réservation dans l'ESB de Res@net.
Actuellement, un message XML est envoyé via une file JMS de l'application Web vers l'application Réservation.
On souhaite que ces messages soient routés via l'ESB.

Voici la requête émise de l'application Web.

	<?xml version="1.0" encoding="UTF-8"?>
	
	<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
	                    xmlns:esb="http://esb.resanet.com"
	                    targetNamespace="http://esb.resanet.com"
	                    elementFormDefault="qualified"
	                    attributeFormDefault="unqualified">
	
	<xs:element name="reservation">
	    <xs:complexType>
	        <xs:sequence>
	            <xs:element name="idReservation" type="xs:string"/>
	            <xs:element name="dateReservation" type="xs:dateTime"/>
	            <xs:element name="produit" maxOccurs="unbounded">
	                <xs:complexType>
	                    <xs:sequence>
	                        <xs:element name="codeProduit" type="xs:string"/>
	                        <xs:element name="codeTarifaire" type="xs:string"/>
	                        <xs:element name="quantite" type="xs:int"/>
	                    </xs:sequence>
	                </xs:complexType>
	            </xs:element>
	        </xs:sequence>
	    </xs:complexType>
	</xs:element>
	
	</xs:schema>

On souhaite interroger la base de données du catalogue et intégrer directement le prix des produits présents dans la réservation
modification de format
le champ codeTarifaire est remplacé par le champ tarif
on fournit une base de données HSQLDB embarquée et chargée


Le format attendu par l'application Réservation est le suivant

	<?xml version="1.0" encoding="UTF-8"?>
	
	<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
	                    xmlns:esb="http://esb.resanet.com"
	                    targetNamespace="http://esb.resanet.com"
	                    elementFormDefault="qualified"
	                    attributeFormDefault="unqualified">
	
	<xs:element name="reservation">
	    <xs:complexType>
	        <xs:sequence>
	            <xs:element name="attributsReservation">
	                <xs:complexType>
	                    <xs:sequence>
	                        <xs:element name="idReservation" type="xs:string"/>
	                        <xs:element name="dateReservation" type="xs:dateTime"/>
	                    </xs:sequence>
	                </xs:complexType>
	            </xs:element>
	            <xs:element name="produits" maxOccurs="unbounded">
	                <xs:complexType>
	                    <xs:sequence>
	                        <xs:element name="codeProduit" type="xs:string"/>
	                        <xs:element name="tarif" type="xs:double"/>
	                        <xs:element name="quantite" type="xs:int"/>
	                    </xs:sequence>
	                </xs:complexType>
	            </xs:element>
	        </xs:sequence>
	    </xs:complexType>
	</xs:element>
	
	</xs:schema>

### 3. Mise en place

Implémenter ce flux métier via l'ESB.


## TP8. Web Services

### 1. Partie 1 - Mise en place d'un Web Service simple

L'objectif de ce TP est de mettre en œuvre les Web Services au sein de notre ESB.

Créer un WSDL (Document/Literal « Wrapped ») nous permettant de récupérer les informations d'un hôtel.
Requête d'entrée : référence de l'hôtel (exemple : REF0001)
Requête de sortie : hôtel (structure composé de 3 éléments : nom, ville et nombre d'étoiles)
Erreur : si l'hôtel est inconnu, une erreur est renvoyée (type chaîne)

Implémenter le service. Vous mettrez en place une Map<String,Hotel> contenant 2 ou 3 hôtels afin de simuler l'accès à une base de données.

Déployer le service dans l'ESB
Ouvrir un navigateur vers l'url http://localhost:8181/cxf/ : cela donne la liste webservices déployés dans le container de servlet du moteur OSGi.

Tester le service via un client Webservice SOAP (soapUI par exemple)
Créer un nouveau projet
Fournir l'adresse du WSDL : [URL_WS]?wsdl (exemple : http://localhost:8080/hotel?wsdl)
Exécuter la requête SOAP


### 2. Partie 2 – Mise en place d'un orchestrateur de Web Services

L'objectif de cette seconde partie est de mettre en oeuvre une orchestration simple de Web Services. Nous nous appuierons pour cela sur un Web Service extérieur qui fournit les hôtels d'un partenaire (2 hôtels sont disponibles : EXT0001 et EXT0002). 

Copier le fichier ws-partenaire.jar et exécuter le

Inspecter le WSDL (ie. http://localhost:11000/partenaire?wsdl) correspondant afin de connaître les opérations disponibles et les types manipulés

Faire évoluer le flux existant afin de prendre en charge le nouveau Web Service : les références commençant par « EXT » seront redirigées vers ce dernier, les autres références seront traitées en interne par le service déployé dans la partie 1.



## TP9. Les SI sont en perpétuelle évolutions

### 1. Objectif

Intégrer un flux de messages complet (ensemble composants, routage).

### 2. Contexte

Le partenaire AirAlpha va faire évoluer son SI. Il va dorénavant exposer des webservices, et ne plus consommer les messages JMS entrants.
Le SI de Res@net doit donc évoluer en conséquence …

Voici le WSDL d'AirAlpha

<?xml version="1.0" encoding="UTF-8"?>

<definitions xmlns="http://schemas.xmlsoap.org/wsdl/"
             xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
             xmlns:xsd="http://www.w3.org/2001/XMLSchema"
             xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
             xmlns:wsa="http://schemas.xmlsoap.org/ws/2003/03/addressing"
             xmlns:tns="http://airalpha.com/services/1.0"
		targetNamespace="http://airalpha.com/services/1.0"
		name="AirAlpha" >

<types>

    <xsd:schema elementFormDefault="qualified"  
                targetNamespace="http://airalpha.com/services/1.0">
        <xsd:element name="requete">
            <xsd:complexType>
                <xsd:sequence>
                    <xsd:element name="dateReception" type="xsd:date"/>
                    <xsd:element name="voyage" type="xsd:string"/>
                </xsd:sequence>
            </xsd:complexType>
        </xsd:element>
        <xsd:element name="reponse">
            <xsd:complexType>
                <xsd:sequence>
                    <xsd:element name="statut" type="xsd:string"/>
                </xsd:sequence>
            </xsd:complexType>
        </xsd:element>
    </xsd:schema>

</types>

<message name="requeteMessage">
    <part name="requete" element="tns:requete"/>
</message>

<message name="reponseMessage">
    <part name="reponse" element="tns:reponse"/>
</message>

<portType name="airAlphaPortType">
    <operation name="reservation">
        <input name="requete" message="tns:requeteMessage"/>
        <output name="reponse" message="tns:reponseMessage"/>
    </operation>
</portType>

<binding name="airAlphaBinding" type="tns:airAlphaPortType">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" 
                  style="document"/>
    <operation name="reservation">
        <soap:operation soapAction="reservation"/>
        <input name="requete">
            <soap:body use="literal"/>
        </input>
        <output name="reponse">
            <soap:body use="literal"/>
        </output>
    </operation>
</binding>

<service name="airAlphaService">
    <port name="airAlphaPort" binding="tns:airAlphaBinding">
        <soap:address location="REPLACE_WITH_ACTUAL_URL"/>
    </port>
</service>

</definitions>

### 3. Mise en place

Faire évoluer le SI pour attaquer les webservices d'AirAlpha.

Note 1 : Pour simuler le webservice d'AirAlpha, on pourra utiliser l'utilitaire SoapUI.


## TP10. Gestion des erreurs

### 1. Objectif

Mettre en place une gestion d'erreur avec Camel.

### 2. Mise en place

Créer un flux de messages décrivant les routes suivantes
message XML d'entrée via une file JMS
ce message est validé sur son schéma XSD par l'ESB
en cas d'erreur de validation, le message est placé dans une file non-valide
s'il est conforme, il est routé vers la file valide

sur toute autre erreur, on souhaite que le message soit routé vers une file dlq

## TP11. Transactions

### 1. Objectif

Mettre en place une solution transactionnelle.

### 2. Contexte

Quand les clients connectés sur l'interface Web valident leur commande, un accusé de réception leur est directement envoyé. La requête HTTP émise est transférée directement à l'application Web au travers une infrastructure réseau.

La requête HTTP est routée vers un service Java qui prend un certain délai avant de retourner son résultat (environ 10 secondes). La réponse de ce service est ensuite placé dans une file JMS.


Après plusieurs problèmes de perte de commandes à la suite de crashs applicatifs, la société Res@net voudrait que vous imaginez une nouvelle solution, plus robuste en cas de crash.

### 3. Mise en place

Réfléchir à l'évolution de la solution pour la rendre robuste même en cas de crash
Implémenter la solution.

## TP12.  Monitoring
L'objectif de ce TP est de mettre en œuvre les différentes techniques de monitoring disponibles dans un ESB.

### 1. Mise en place d'un flux simple

Nous allons, dans un premier temps, mettre en place un flux JMS.
Nous ferons transiter un message de demande d'information (exemple : <REF>voy_001</REF>) dans une première file (nom de la file : file.demande.informations). 

Puis nous mettrons en place un traitement factice (exemple :
LogFactory.getLog(Classe.class).info("TRAITEMENT : " + message)).

Enfin nous transmettrons les réponses (ie. requête) à la file file.reponse.informations. 

Afin de tester ce flux, nous utiliserons Jconsole.
JConsole est un client JMX fourni avec le JDK 6. Nous l'utiliserons pour envoyer des messages sur la file d'entrée et pour lire/purger les messages de sorties.

Remarque : la classe d'entrée JMX de ServiceMix est org.codehaus.classworlds.Launcher

### 2. Pattern WireTap

Mettre en place un « wiretap » entre les 2 files JMS
Envoyer les messages sur une file file.monitoring.demande
Sauvegarder les message de cette file dans des fichiers
Tester

### 3. Pattern Detour

Mettre en place, juste après le « wiretap », un système de « detour »
Ce « detour » redirige les requêtes vers un noeud Camel de validation (idem que pour le traitement : validation factice)
Afin de contrôler le détour, créer un bean dédié contenant un attribut booléen detour et l'utiliser pour router ou non les messages vers la validation
Afin de modifier le paramètre de detour, mettre en place un WSDL via CXF afin de venir modifier directement le bean contenant l'attribut.

### 4. Client JMX

Créer un client JMX permettant d'afficher le nombre de requêtes ayant transitées dans la file d'entrée
Tester



