import base64

from zeep import Client

try:
    client = Client('http://localhost:7777/ws/SocialWebService?wsdl')
except Exception:
    print("El servidor no está corriendo o tiene problema el puerto")
    exit()


def errorhandler(user):
    try:
        int(user)
        return True
    except ValueError:
        return False


def getPosts(user):

    if not errorhandler(user):
        return print(client.service.getUserPosts(user))
    else:
        print("No se aceptan números como nombre de usuario")


def createPost(content, image, user):
    try:
        if not errorhandler(user):
            client.service.createPost(content,image,user)
            print("El post ha sido creado por el usuario {}".format(user))
            return ""
    except Exception:
        print("Ha ocurrido algún problema con el servicio, intentalo nuevamente y revisa tus parametros")


while True:
    print("Menú:")
    print("1- Listar posts dado un usuario")
    print("2- Crear un nuevo post")
    print("3- Salir")

    opcion = input("Introduzca la opción deseada: ")

    if "0" < opcion <= "3":
        if opcion == "1":
            tempUser = input("Introduzca el usuario a buscar posts: ")
            getPosts(tempUser)
        elif opcion == "2":
            tempContent = input("Introduzca el contenido en texto del post: ")
            try:
                tempimage = input("Introduzca la ruta de la imagen: ")
                with open(tempimage, "rb") as imageFile:
                    image_b64 = base64.b64encode(imageFile.read())

                user = input("Introduzca el usuario que lo está creando: ")
                createPost(tempContent, image_b64, user)
            except:
                user = input("Introduzca el usuario que lo está creando: ")
                image_b64 = ""
                createPost(tempContent, image_b64, user)
        elif opcion == "3":
            break
    else:
        print("Esta opción no existe")
#
# print(client.service.getUserPosts("carlp"))
#
# # print(client.service.createPost("Mi primer Post por SOAP", "Este post fue generado a través de SOAP utilizando Python", "", "carlp"))