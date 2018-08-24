$(document).ready(function() {
    $('#registerForm').validate({
        groups: {
            fullName: 'firstName lastName'
        },
        rules: {
            firstName: {
                require_from_group: [2, '.fullName']
            },
            lastName: {
                require_from_group: [2, '.fullName']
            },
            username: {
                required: true,
                minlength: 4,
                remote: {
                    url: 'isUsernameAvailable',
                    type: 'post',
                    data: {
                        username: function () {
                            return $( '#username' ).val();
                        }
                    }
                }
            },
            password: {
                required: true,
                minlength: 8
            },
            confirmPassword: {
                required: true,
                minlength: 8,
                equalTo: '#password'
            },
            bornDate: {
                required: true,
                dateISO: true
            },
            city: 'required'
        },
        messages: {
            firstName: {
                require_from_group: 'Campos requeridos'
            },
            lastName: {
                require_from_group: 'Campos requeridos'
            },
            username: {
                required: 'Por favor, ingresa un nombre de usuario.',
                minlength: 'El nombre de usuario debe tener al menos 4 caracteres'
            },
            password: {
                required: 'Por favor, ingresa una contraseña',
                minlength: 'La contraseña debe contener al menos 8 caracteres'
            },
            confirmPassword: {
                required: 'Por favor, ingresa una contraseña',
                minlength: 'La contraseña debe contener al menos 8 caracteres',
                equalTo: 'La contraseña ingresada no coincide con el campo anterior'
            },
            bornDate: {
                required: 'Por favor, ingresa una fecha de nacimiento',
                dateISO: 'Por favor, ingresa una fecha valida'
            },
            city: 'Por favor, ingresa una ciudad'
        }
    });
});