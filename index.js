'use-strict'

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.database.ref('/notificationsRequests/{username}/{notification_id}/').
onWrite(async (data,context)  =>
{
    const username = context.params.username;
    const notification_id = context.params.notification_id;

    const m = admin.database().ref(`/notificationsRequests/${username}/${notification_id}`).once('value');
    const r = await m;
    const message = r.val();
    if(!data.after.val())
    {
       console.log('A notification deleted');
       return null;
    }
    const deviceToken = admin.database().ref(`/Users/${username}/token`).once('value');

    const result = await deviceToken;
    const token = result.val();
    const payload = {
        notification: {
            title: "New Task",
            body: message
        }
    };
    const response = await admin.messaging().sendToDevice(token, payload);
    console.log("A Notification");   
});












 // const username = context.params.username;
    // const notification_id = context.params.notification_id;

    // console.log("We have a notification for "+username+" with id: "+notification_id)    

    // return admin.database.ref(`/Users/${username}/token`).once('value').then(result =>
    //     {
    //         const token = result.val();
    //         const payload = {
    //             notification:{
    //                 title: "New task",
    //                 body : data.val() 
    //             }
    //         };
    //         return admin.messaging.sendToDevice(token,payload).then(result =>{

    //                 console.log("Notification Sent")    
    //         });


    //     });

    // /*if(!data.after.val())
    // {
    //  console.log('A notification deleted');
    //  return null;
    // }
    // const deviceToken = admin.database.ref(`/Users/${username}/device_token`).once('value');
    // return deviceToken.then(result => {
    //     const token_id= result.val();
    //     const payload = {
    //         notificaion:
    //         {
    //             title:'New Chat request',
    //             body:`New chat request`
    //         }
    //     };
    //     return admin.messaging().sendToDevice(token_id,payload).then(response =>{
    //         console.log("Notification");
    //     });   
    // });*/
