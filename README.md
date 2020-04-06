# Dixit Retro

This is a simple Android project that will enable remote teams to play the retrospective Dixit

## What is Dixit?

It's a great tabletop game with the prettiest cards of all.
https://en.wikipedia.org/wiki/Dixit_(card_game)


## What is Dixit Retrospective

It's a retrospective that use the Dixit cards as a way to gather informations about a sprint.
The cards are a great way to gather data in a different and inspiring way.

https://www.agilelearninglabs.com/2013/07/dixit-sprint-retrospective-game/
https://blog.myagilepartner.fr/index.php/2019/11/08/retrospective-dixit/


## How to prepare the game

This application requires Firebase to save the users selections.
You need to create a Firebase project and generate a google-services.json file for the application id (com.example.retrodixit).
This file goes in the app folder.
You can then create the apk and give it to all the participants.
 
https://firebase.google.com/docs/projects/learn-more?authuser=0#setting_up_a_firebase_project_and_adding_apps
 
 
## How to play

### Step one: selecting the card (5min)
1) Enter your name
2) Choose one card in the list that reflects the most the sprint (a feeling, an event, something good, something bad...)
3) Click "Select the card" to validate your choice. It will appears as "ready" for the others participants
4) When everyone has selected their card, you can now starts step 2

### Step Two: Gathering data (25min)
5) When clicking on "show", the other participant will be able to see your card (eye)
6) Every one look at the card of the same user and tries to guess the meaning of the card
7) The ideas must be written
8) After all ideas has been said (or timeout), the card owner will reveal why he choose the card

Do this for everyone. 

If you have time or you need more, you can do another round.
When this is over, you can do the usual (group by themes, create actions...)
 
## I want to adapt it to my needs

Sure, go ahead.

###### I want to add/remove cards
The cards can be found in app/src/main/assets/cards
Format: jpg
Dimensions: 250x375 px
Name: card_xxxxx.jpg

Just make sure all the cards are in consecutive order.

## Licences / Copyright

I got the cards come from the projet [dixit-online](https://github.com/jminuscula/dixit-online), that took them from [Here](https://www.pinterest.es/search/pins/?0=dixit%7Ctyped&1=card%7Ctyped&q=dixit%20card&rs=typed).
This projet is under [Creative Commons](https://creativecommons.org/licenses/by-nc-sa/2.0/) license.
We do not own the images. If you're feeling artsy, you can add your own.

Honestly, just buy the game if you can, it's really good. 