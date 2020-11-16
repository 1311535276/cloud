Toast ={
    success: function(message) {
        Swal.fire({
            position: 'top-end',
            icon:'success',
            type: 'success',
            title:message,
            showConfirmButton: false,
            timer: 3000
        })
    },
    error: function (message){
        Swal.fire({
            position: 'top-end',
            icon:'error',
            type: 'error',
            title: message,
            showConfirmButton: false,
            timer: 3000
        })

    },
    warning: function (message){
        Swal.fire({
            position: 'top-end',
            icon:'warning',
            type: 'warning',
            title: message,
            showConfirmButton: false,
            timer: 3000
        })

    }
}