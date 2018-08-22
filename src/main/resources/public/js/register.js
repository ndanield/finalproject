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
                minlength: 2
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
                require_from_group: 'Ambos nombres son requeridos, por favor completalos.'
            },
            lastName: {
                require_from_group: 'Ambos nombres son requeridos, por favor completalos.'
            },
            username: {
                required: 'Por favor, ingresa un nombre de usuario.',
                minlength: 'El nombre de usuario debe tener al menos 2 caracteres'
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