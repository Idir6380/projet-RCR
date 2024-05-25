from owlready2 import *

# Créer une nouvelle ontologie
onto = get_ontology("http://naruto.org/onto.owl")

with onto:  # Définir notre ontologie

    # Définir les concepts atomiques
    class Humain(Thing):
        pass

    class Ninja(Humain):
        pass

    class Jinchuriki(Ninja):
        pass

    class Clan(Thing):
        pass

    class Village(Thing):
        pass

    # Définir les propriétés
    class aPourPouvoir(DataProperty):
        domain = [Ninja]
        range = [str]

    class membreDe(DataProperty):
        domain = [Ninja]
        range = [str]

    class membreDuClan(ObjectProperty):
        domain = [Ninja]
        range = [Clan]

    class membreDuVillage(ObjectProperty):
        domain = [Ninja]
        range = [Village]

    # Définir les instances (ABox)
    naruto = Jinchuriki("naruto")
    naruto.aPourPouvoir.append("Rasengan")
    naruto.aPourPouvoir.append("Shadow Clone Technique")
    naruto.aPourPouvoir.append("Sage Mode")

    sasuke = Ninja("sasuke")
    sasuke.aPourPouvoir.append("Sharingan")
    sasuke.aPourPouvoir.append("Chidori")
    sasuke.aPourPouvoir.append("Susanoo")

    konoha = Village("Konoha")
    uzumaki = Clan("Uzumaki")
    uchiha = Clan("Uchiha")

    naruto.membreDe.append("Konoha")
    sasuke.membreDe.append("Konoha")

    naruto.membreDuClan.append(uzumaki)
    naruto.membreDuVillage.append(konoha)
    sasuke.membreDuClan.append(uchiha)
    sasuke.membreDuVillage.append(konoha)

    # Ajouter l'assertion négative que Sasuke n'est pas un Jinchuriki
    AllDisjoint([Jinchuriki, sasuke])

    # Synchroniser et raisonner
    sync_reasoner_pellet(infer_property_values=True)

    # Vérifier les assertions
    print("Naruto est un humain:", naruto in Humain.instances())
    print("Naruto est un ninja:", naruto in Ninja.instances())
    print("Naruto est un jinchuriki:", naruto in Jinchuriki.instances())
    print("Sasuke est un humain:", sasuke in Humain.instances())
    print("Sasuke est un ninja:", sasuke in Ninja.instances())
    print("Sasuke est un jinchuriki:", sasuke in Jinchuriki.instances())

# Sauvegarder l'ontologie
onto.save(file="naruto.owl", format="rdfxml")

# Charger l'ontologie et vérifier les assertions
onto.load()
with onto:
    # Refaire le raisonnement
    sync_reasoner_pellet(infer_property_values=True)

    naruto = onto.search_one(iri="*naruto")
    sasuke = onto.search_one(iri="*sasuke")

    print("Naruto est un humain:", naruto in Humain.instances())
    print("Naruto est un ninja:", naruto in Ninja.instances())
    print("Naruto est un jinchuriki:", naruto in Jinchuriki.instances())
    print("Sasuke est un humain:", sasuke in Humain.instances())
    print("Sasuke est un ninja:", sasuke in Ninja.instances())
    print("Sasuke est un jinchuriki:", sasuke in Jinchuriki.instances())

    # Afficher les pouvoirs, le clan et le village de Naruto
    print("Pouvoirs de Naruto:", list(naruto.aPourPouvoir))
    print("Affiliation de Naruto au village:", list(naruto.membreDuVillage))
    print("Affiliation de Naruto au clan:", list(naruto.membreDuClan))

    # Afficher les pouvoirs, le clan et le village de Sasuke
    print("Pouvoirs de Sasuke:", list(sasuke.aPourPouvoir))
    print("Affiliation de Sasuke au village:", list(sasuke.membreDuVillage))
    print("Affiliation de Sasuke au clan:", list(sasuke.membreDuClan))
