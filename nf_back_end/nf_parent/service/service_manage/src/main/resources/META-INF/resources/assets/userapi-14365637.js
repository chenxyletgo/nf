import{v as e}from"./base-79093b69.js";const s="/manservice/user",d=t=>e({url:s+"/login",method:"post",data:t}),m=(t,a,o)=>e({url:s+"/adm/getUserList/"+a+"/"+o,method:"post",data:t}),l=(t,a)=>e({url:s+"/adm/setUserState/"+a,method:"put",data:{id:t}}),n=t=>e({url:s+"/adm",method:"delete",data:{id:t}});export{n as d,m as g,d as l,l as s};
