import{d as E,r as c,z as x,w as V,o as N,e as v,f as r,C as p,k as l,l as n,c as u,F as A,g as U,m as I,t as f,G as h,K as M,_ as P}from"./index-7043f5d2.js";import{a as S,E as T,b as F}from"./el-select-790cb6c3.js";import"./el-input-ea2dabc9.js";import"./el-scrollbar-a3a91b86.js";import{E as L,a as j}from"./el-table-column-ca2f0dab.js";import"./el-checkbox-99e20c18.js";import{e as D}from"./articleapi-65b59442.js";import{b as G,d as b}from"./commentapi-08f1f9c1.js";import"./_initCloneObject-cf0c2ff8.js";const K={class:"content-div"},O={class:"head"},W={class:"select-div"},$=E({__name:"Cmet",setup(q){const _=c(),m=c(),t=x({total:0,cur:1,size:14}),i=c();function d(s=1){G(i.value,s,t.size).then(a=>{m.value=a.data.list,t.total=a.data.total})}function w(){D().then(s=>{_.value=s.data.list})}function C(s){t.total%t.size===1&&Math.floor(t.total/t.size)!==0?b(s).then(a=>{h.success(a.message),t.cur--,d(t.cur)}):b(s).then(a=>{h.success(a.message),d(t.cur)})}return V(i,()=>{d()}),N(()=>{w()}),(s,a)=>{const y=F,k=S,o=j,g=M,z=L,B=T;return u(),v("div",null,[r("div",K,[r("div",O,[p(" 评论管理 "),r("div",W,[l(k,{modelValue:i.value,"onUpdate:modelValue":a[0]||(a[0]=e=>i.value=e),class:"m-2",placeholder:"选择文章"},{default:n(()=>[(u(!0),v(A,null,U(_.value,e=>(u(),I(y,{key:e.id,label:e.title,value:e.id},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])])]),r("div",null,[l(z,{data:m.value,style:{width:"100%"}},{default:n(()=>[l(o,{type:"index",label:"编号",width:"60"}),l(o,{"show-overflow-tooltip":!0,prop:"words",label:"标题",width:"150"}),l(o,{prop:"userName",label:"发言人",width:"100"},{default:n(e=>[r("span",null,f(e.row.userName),1)]),_:1}),l(o,{prop:"level",label:"级别",width:"100"},{default:n(e=>[r("span",null,f(e.row.level===0?"一级":"二级"),1)]),_:1}),l(o,{prop:"like",label:"赞",width:"100"}),l(o,{prop:"dislike",label:"踩",width:"100"}),l(o,{prop:"time",label:"发布时间",width:"180"}),l(o,{prop:"id",fixed:"right",label:"操作",width:"110"},{default:n(e=>[l(g,{link:"",type:"primary"},{default:n(()=>[p("查看")]),_:1}),l(g,{onClick:H=>C(e.row.id),link:"",type:"primary"},{default:n(()=>[p("删除")]),_:2},1032,["onClick"])]),_:1})]),_:1},8,["data"]),l(B,{class:"page-div","current-page":t.cur,"onUpdate:currentPage":a[1]||(a[1]=e=>t.cur=e),"page-size":t.size,"onUpdate:pageSize":a[2]||(a[2]=e=>t.size=e),layout:"prev, pager, next, jumper",total:t.total,background:!0,onCurrentChange:d},null,8,["current-page","page-size","total"])])])])}}});const le=P($,[["__scopeId","data-v-63051c8d"]]);export{le as default};
