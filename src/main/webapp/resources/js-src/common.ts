$(function () {
    console.log("Everything Start!");
});

//global functions
function confirmLogout() {
    bootbox.confirm('Are you sure logout?', (result: boolean) => {
        if (result) {
            $('#logoutForm').submit();
        }
    });
}
