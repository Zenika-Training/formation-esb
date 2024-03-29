# L'architecture d'un ESB

<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

- [00 – Agenda](#/0)
- [01 – De la difficulté de définir un ESB](#/1)
- [02 – Comprendre les principes fondamentaux d'un ESB](#/2)
- **[03 – L'architecture d'un ESB](#/3)**
- [04 – Un ESB Open Source](#/4)
- [05 – Apache ServiceMix](#/5)
- [06 – Apache Camel](#/6)
- [07 – Apache ActiveMq](#/7)
- [08 – Connectivité](#/8)
- [09 – Routage, transformations et intégration](#/9)
- [10 – Web Services](#/10)
- [11 – Déployer un environnement de qualité, robuste et fiable](#/11)
- [12 – Surveiller l'ESB : Monitoring métier et technique](#/12)



## Image pleine page en markdown

![Logo Zenika](ressources/logo-zenika.jpg)



## Image pleine page en HTML

<figure>
    <img src="ressources/logo-zenika.jpg" alt="Logo Zenika"/>
</figure>



## Image centrée

<figure style="margin-top: 20%;">
    <img src="ressources/logo-zenika.jpg" alt="Logo Zenika"/>
</figure>



## Image pleine page avec légende

<figure>
    <img src="ressources/logo-zenika.jpg" alt="Logo Zenika"/>
    <figcaption>Le logo Zenika</figcaption>
</figure>



## Image avec légende dans un paragraphe

Le logo Zenika à partager sur tous les réseaux. 

<figure style="margin-top: 5%; margin-bottom: 5%;">
    <img src="ressources/logo-zenika.jpg" alt="Logo Zenika"/>
    <figcaption>Le logo Zenika</figcaption>
</figure>

*Note* : Utiliser la balise `<figcaption>` 



## Image en petit

L'image suivante a une largeur de 25%

<figure>
    <img src="ressources/logo-zenika.jpg" alt="Logo Zenika" width="25%"/>
    <figcaption>Le logo Zenika</figcaption>
</figure>



## Image avec un positionnement absolu

Avec le style 

```css
position: absolute; 
bottom: 30px; 
right: 0;
```

<figure style="position: absolute; bottom: 30px; right: 0;">
    <img src="ressources/logo-zenika.jpg" alt="Logo Zenika"/>
</figure>



## Positionnement en colonne

<!-- .element: style="display: block; float: left; margin: 30px 0; width: 60%" -->
Texte dans la colonne de gauche avec logo à droite.

<figure style="display: block; float: left; margin: 30px 0; width: 40%">
    <img src="ressources/logo-zenika.jpg" alt="Logo Zenika" style="margin: auto;"/>
</figure>

<figure style="display: block; float: left; margin: 30px 0; width: 40%">
    <img src="ressources/logo-zenika.jpg" alt="Logo Zenika" style="margin: auto;"/>
</figure>

<!-- .element: style="display: block; float: left; margin: 30px 0; width: 60%" -->
Texte dans la colonne de droite avec logo à gauche.

<!-- .element: style="clear: left;" -->
Le dernier paragraphe n'est pas dans un block et se place donc en dessous des 2 colonnes, et en pleine largeur.



## Positionnement absolu en colonne

Le logo aligné à droite est en 

positionnement absolu.

Le texte passe par dessous l'image et le retour à la ligne doit donc être traité manuellement.

<figure style="position: absolute; top: 130px; right: 30px;">
    <img src="ressources/logo-zenika.jpg" alt="Logo Zenika"/>
</figure>



<!-- .slide: class="page-title" -->



<!-- .slide: class="page-tp3" -->
