# Apache ActiveMq

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
- **[07 – Apache ActiveMq](#/7)**
- [08 – Connectivité](#/8)
- [09 – Routage, transformations et intégration](#/9)
- [10 – Web Services](#/10)
- [11 – Déployer un environnement de qualité, robuste et fiable](#/11)
- [12 – Surveiller l'ESB : Monitoring métier et technique](#/12)



## Contenu

- Présentation d'un Broker
- Normes et usages
- Brokers Open-Source
- JMS
- ActiveMq 


## Présentation d'un broker

Todo Présenter :
Les uses cases
Les modes de communication (P2P, RPC, Broadcast)
Comment est gérée la mémoire (ram, disque, indexes, pagination, ...)
Clustering



## Les modes de communication

blabla



## Point To Point 

blabla



## RPC

blabla



## Broadcast / Multicast

blabla



<!-- .slide: class="page-questions" -->



## Normes et usages

JMS
AMQP
Autre Kafka ...



<!-- .slide: class="page-questions" -->



## Brokers Open-Source


JMS : Apache ActiveMq, JBoss HornetQ, ....
AMQP : RabbitMq, Apache QPid
Autres : Apache Kafka



<!-- .slide: class="page-questions" -->



##JMS

présenter : 
Connexion
Session
Transaction
Acknowledges
...



<!-- .slide: class="page-questions" -->



## Apache ActiveMq

- Serveur de messages
- Compatible spécifications JMS
- Performant et fiable (persistance KahaDB)
- Clustering / Haute Disponibilité : 
	- Master / Slave
	- Réseau de Brokers
- Supervision : 
	- Via la console
	- Via JMX



## Apache ActiveMq



## TP 3

<!-- .slide: class="page-tp3" -->
