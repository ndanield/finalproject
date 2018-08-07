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
            client.service.createPosts(content,image,user)
            return "El post ha sido creado por el usuario {}".format(user)
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
            tempimage = input("Introduzca la ruta de la imagen: ")
            user = input("Introduzca el usuario que lo está creando")
            createPost(tempContent, tempimage, user)
        elif opcion == "3":
            break
    else:
        print("Esta opción no existe")
#
# print(client.service.getUserPosts("carlp"))
#
# # print(client.service.createPost("Mi primer Post por SOAP", "Este post fue generado a través de SOAP utilizando Python", "", "carlp"))