# Pokedex java boilerplate

# Introduction

Cet exercice consiste à implémenter le serveur back-end des pokédexs de Kanto.

Il s'agit d'un serveur REST dont chacun des endpoints est décrit dans les users stories ci-dessous.

Chaque user story rapporte un certain nombre de points. Si les tests automatisés associés à cette user story fonctionnent, ces points vous seront accordés. Si une partie d'entres eux fonctionnent mais pas tous, vous marquerez des points au prorata des tests passés.

Par ailleurs 5 points seront réservés à la qualité du code implémenté selon les critères suivants:
- Architecture de l'application
- Maintenabilité du code
- Pertinence des choix des containers
- Threadsafety
- Gestion des exceptions

| User story    | Points |
| ------------- | ---- |
| User Story 1 | 5 |
| User Story 2 | 4 |
| User Story 3 | 3 |
| User Story 4 | 3 |

Le boiler plate associé à ce README vous est fourni comme point de départ avec un endpoint `/api/status` qui ne doit pas être modifié. Des commentaires vous permettant de vous aider à récupèrer les données reçues avec le framework Javalin ont été placés dans le fichier `App.java`.

# Description de l'application

Un pokédex est une console qui permet de consulter les caractéristiques d'un pokémon. On pourra ainsi ajouter des pokémons et consulter les caractérisques d'un ou de plusieurs pokémons.
Les modèles de données utilisés pour cet exercice sont présentés ci-dessous.

## Pokémon

Le nom du pokémon servira d'identifiant unique dans le cadre de cet exercice.

| Nom de l'attribut | Type |
| ------------- | ------------ |
| pokemonName | String |
| type | Elements |
| lifePoints | int |
| powers | List\<Power> |

## Elements
Les différents éléments sont:
- NEUTRAL
- FIRE
- WATER
- GRASS
- ELECTRIC
- ICE
- FIGHTING
- POISON
- GROUND

## Power
| Nom de l'attribut | Type |
| ------------- | ------------ |
| powerName | String |
| damageType | Elements |
| damage | int |

# US 1 - Création d'un pokémon

En tant qu'utilisateur, je souhaite ajouter un pokémon dans la base du Pokédex. J'envoie une requête Json de type `POST` contenant les informations nécessaires à la création.
- Le nom (servant d'identifiant)
- Le type
- Le nombre de points de vie
- La liste des capacités

Si le pokémon existe déjà ou que le json est incomplet ou invalide, une erreur 400 est renvoyée par le serveur.
En cas de réussite, le code 200 est envoyé par le serveur.

Le endpoint à utiliser est `/api/create`

## Spécifications d'interfaces
### Requête

```json
{
    "pokemonName": "Pikachu",
    "type": "ELECTRIC",
    "lifePoints": 70,
    "powers": [
        {
            "powerName": "gnaw",
            "damageType": "NEUTRAL",
            "damage": 30
        }
    ]
}
```

# US 2 - Recherche de pokémons par nom

En tant qu'utilisateur, je souhaite récupérer une liste de pokémons correspondants à certains critères. J'envoie une requète de type `GET` sur le endpoint `/api/searchByName?name=nameToSearch` ayant pour paramètre `name` contenant la chaine de caractères à rechercher.

Le serveur doit envoyer la liste des pokémons pour lesquelles la chaine de caractères fournie par l'utilisateur **est contenue** dans le nom du pokémon. La recherche est sensible à la casse.

Si aucun pokémon ne correspond à cette chaine, une liste vide est renvoyée avec le code 200.

Si le paramètre est invalide (par exemple de mauvais type), le serveur répond avec le code d'erreur 400.

Exemple de requête : `/api/searchByName?name=Pika`

## Spécifications d'interfaces
### Réponse

```json
{
    "result": [
        {
            "pokemonName": "Pikachu",
            "type": "ELECTRIC",
            "lifePoints": 80,
            "powers": [
                {
                    "powerName": "gnaw",
                    "damageType": "NEUTRAL",
                    "damage": 30
                },
                {
                    "powerName": "thunder jolt",
                    "damageType": "ELECTRIC",
                    "damage": 50
                }
            ]
        }
    ]
}
```

# US 3 - Recherche de pokémons par type

En tant qu'utilisateur, je souhaite récupérer une liste de pokémons appartenant au même type. J'envoie une requète de type `GET` ayant pour paramètre le `type` contenant le type à rechercher.

Le serveur doit envoyer la liste des pokémons qui sont du type recherché.

Si aucun pokémon ne correspond à ce type, une liste vide est renvoyée avec le code 200.

Si le type recherché n'est pas dans la liste de type possible, le serveur renvoie une requête vide avec le code d'erreur 400.

Exemple de requête : `/api/searchByType?type=ELECTRIC`

## Spécifications d'interfaces

### Réponse
```json
{
    "result": [
        {
            "name": "Pikachu",
            "lifePoints": 80,
            "powers": [
                {
                    "name": "gnaw",
                    "damageType": "NEUTRAL",
                    "damage": 30
                },
                {
                    "name": "thunder jolt",
                    "damageType": "ELECTRIC",
                    "damage": 50
                }
            ]
        }
    ]
}
```

# US 4 - Modification d'un pokémon

En tant qu'utilisateur, je souhaite modifier un pokémon dans la base du Pokédex. J'envoie une requête Json de type `POST` contenant le nom du pokémon et les informations à modifier. Tous les attributs d'un pokémon peuvent être modifiés sauf le nom.

En ce qui concerne la liste de capacité, si la capacité existe déjà, elle n'est pas modifiée. On ne peut qu'ajouter des nouvelles capacités, pas modifier les existantes.

Si la modification est effectuée, le serveur répond avec le code 200.
Si le pokémon n'existe pas, le serveur répond avec le code 404.
Si le json est invalide, le serveur répond avec le code 400.

Le endpoint à utiliser est `/api/modify`

## Spécifications d'interfaces
### Requête example 1

```json
{
    "pokemonName": "Pikachu",
    "lifePoints": 80
}
```

### Requête example 2

```json
{
    "pokemonName": "Pikachu",
    "powers": [
        {
            "powerName": "thunder jolt",
            "damageType": "ELECTRIC",
            "damage": 50
        }
    ]
}
```

