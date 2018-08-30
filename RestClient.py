import base64

import unirest
import json


def getPosts(user):
    response = unirest.get("https://banagreen.herokuapp.com/rest/userPosts/{}".format(user), headers={"Accept":"application/json"})
    return response.raw_body


# def getPosts(user):
#     response = unirest.get("http://localhost:4567/rest/userPosts/{}".format(user), headers={"Accept":"application/json"})
#     return response.raw_body


def createPost(content, image, user):
    response = unirest.post("https://banagreen.herokuapp.com/rest/userPosts/createNewPost", headers={"Content-Type":"application/json", "Accept":"application/json"}, params=json.dumps({"content": "{}".format(content),
                                                                                                                            "image": "{}".format(image),
                                                                                                                            "username": "{}".format(user)}))
    return response.raw_body

# def createPost(content, image, user):
#     response = unirest.post("http://localhost:4567/rest/userPosts/createNewPost", headers={"Content-Type":"application/json", "Accept":"application/json"}, params=json.dumps({"content": "{}".format(content),
#                                                                                                                             "image": "{}".format(image),
#                                                                                                                             "username": "{}".format(user)}))
#     return response.raw_body


print "Bienvenido al cliente REST"

while True:
    print("\nOpciones disponibles:")
    print("1- Obtener el listado de posts dado un usuario")
    print("2- Crear un post")
    print("3- Salir")

    opcion = raw_input("Elige una opcion:")

    if opcion == "1":
        user = raw_input("Introduzca nombre de usuario:")
        print getPosts(user)
    elif opcion == "2":
        content = raw_input("Introduce el contenido en texto:")
        try:
            image = raw_input("Introduzca ruta de imagen:")

            with open(image, "rb") as imageFile:
                image_b64 = base64.b64encode(imageFile.read())
            user = raw_input("introduzca usuario:")
            print createPost(content, image_b64, user)
        except:
            user = raw_input("introduzca usuario:")
            image_b64 = ""
            print createPost(content, image_b64, user)
    elif opcion == "3":
        break