var _ntes_nacc,_ntes_nvid="VISITOR_CLIENT_NO_COOKIE_SUPPORT",_ntes_nvtm=0,_ntes_nvfi=0,_ntes_nvsf=0,_ntes_nstm=0,_ntes_nurl="",_ntes_ntit="",_ntes_nref="",_ntes_nres="",_ntes_nlag="",_ntes_nscd="",_ntes_nlmf=0,_ntes_flsh="",_ntes_nssn="",_ntes_surv=0;function _ntes_void(){}var _ntes_domain_array=['163.com','188.com','netease.com','126.com','126.net','nease.net','yeah.net','gz2010.cn','co188.com','warcraftchina.com','youdao.com'],_ntes_cdmn=ntes_get_domain(),_ntes_src_addr="//analytics.163.com";function neteaseTracker(){_ntes_nurl=escape(document.location);_ntes_ntit=escape(document.title);_ntes_nref=escape(document.referrer);_ntes_flsh=ntes_get_flashver();var now=(new Date()).getTime();document.cookie="__ntes__test__cookies="+now;var _ntes_cookie_enabled=(ntes_get_cookie("__ntes__test__cookies")==now)?true:false;if(_ntes_nacc=="undefined"||!_ntes_nacc)return;if(_ntes_nurl.indexOf("http")!=0)return;var run_flag=0;for(i=0;i<_ntes_domain_array.length;i++){if(_ntes_cdmn=="."+_ntes_domain_array[i]){run_flag=1;break}}if(run_flag==1){var _ck_str=ntes_get_cookie("_ntes_nnid");if(_ck_str==-1){if(_ntes_cookie_enabled){_ntes_nvid=fetch_visitor_hash();_ntes_nvfi=1;ntes_set_cookie_long("_ntes_nnid",_ntes_nvid+",0")}}else{var _id_pos=_ck_str.indexOf(","),_suv_pos=_ck_str.indexOf("|");if(_suv_pos==-1)_suv_pos=_ck_str.length;_ntes_nvid=_ck_str.substr(0,_id_pos);_ntes_surv=_ck_str.substr(_id_pos+1,_suv_pos-_id_pos-1);if(_ntes_surv==''||(_ntes_surv!=0&&(now-_ntes_surv)>365*86400)){_ntes_surv=0}ntes_set_cookie_long("_ntes_nnid",_ntes_nvid+","+_ntes_surv)}_ntes_nssn=ntes_get_cookie("P_INFO");if(_ntes_nssn==-1){_ntes_nssn=""}else{_ntes_nssn=_ntes_nssn.substr(0,_ntes_nssn.indexOf("|"))}ntes_get_navigation_info();var _ntes_q="_nacc="+_ntes_nacc;_ntes_q+="&_nvid="+_ntes_nvid+"&_nvtm="+_ntes_nvtm;_ntes_q+="&_nvsf="+_ntes_nvsf+"&_nvfi="+_ntes_nvfi;_ntes_q+="&_nlag="+_ntes_nlag+"&_nlmf="+_ntes_nlmf;_ntes_q+="&_nres="+_ntes_nres+"&_nscd="+_ntes_nscd;_ntes_q+="&_nstm="+_ntes_nstm;_ntes_q+="&_nurl="+_ntes_nurl+"&_ntit="+_ntes_ntit;_ntes_q+="&_nref="+_ntes_nref+"&_nfla="+_ntes_flsh;_ntes_q+="&_nssn="+_ntes_nssn;_ntes_q+="&_nxkey="+(Number(new Date())+''+Math.random()).substring(6,20)+"&_end1";var _img=new Image();_img.src=_ntes_src_addr+"/ntes?"+_ntes_q;_img.onload=function(){_ntes_void()};if(1){if(_ntes_nacc!="analytics"&&_ntes_nacc!="research"&&_ntes_nurl.indexOf("https")!=0){ntes_survey_popup()}}if(Math.random()>0.5){if(_ntes_cookie_enabled&&_ntes_nurl.indexOf("https")!=0){var _f_q="http://adc.163.com/ntesc?"+"s="+_ntes_nacc,_img_f=new Image();_img_f.src=_f_q;_img_f.onload=function(){_ntes_void()}}}}}function ntes_survey_popup(){if(_ntes_surv==0){if(typeof(_ntes_nvid)=="undefined"||_ntes_nvid.length!=32||_ntes_nvid.substr(30,2)!="23")return;var _ntes_survey_url="//research.163.com/survey/";_ntes_survey_url=_ntes_survey_url+"?_nacc="+_ntes_nacc+"&_nvid="+_ntes_nvid;window.open(_ntes_survey_url,'','width=680,height=450,top=100,left=120,scrollbars=yes');ntes_set_cookie_long("_ntes_nnid",_ntes_nvid+","+(new Date).getTime())}}function ntes_get_navigation_info(){_ntes_nres="-";_ntes_nscd="-";_ntes_nlag="-";if(self.screen){_ntes_nres=screen.width+"x"+screen.height;_ntes_nscd=screen.colorDepth+"-bit"}else if(self.java){var j=java.awt.Toolkit.getDefaultToolkit(),s=j.getScreenSize();_ntes_nres=s.width+"x"+s.height}if(navigator.language){_ntes_nlag=navigator.language.toLowerCase()}else if(navigator.browserLanguage){_ntes_nlag=navigator.browserLanguage.toLowerCase()}var d=new Date(document.lastModified);_ntes_nlmf=d.getTime()/1000}function fetch_visitor_hash(){var d=new Date(),xy=document.body.clientWidth+":"+document.body.clientHeight,s=str_to_ent(d.getTime()+Math.random()+document.location+document.referrer+screen.width+screen.height+navigator.userAgent+document.cookie+xy);return ntes_hex_md5(s)}function ntes_get_domain(){var domain_name=document.domain,arr_domain_name=domain_name.split("."),domain_name_length=arr_domain_name.length,pattern=/^\d+$/g;if(pattern.test(arr_domain_name[domain_name_length-1])){return domain_name}if(arr_domain_name.length<3){return "."+domain_name}var domain_suffixes=['com','net','org','gov','co'],i,suffix_found=false;for(i=0;i<domain_suffixes.length;i++){if(arr_domain_name[domain_name_length-2]==domain_suffixes[i]){suffix_found=true}}if(!suffix_found){return "."+arr_domain_name[domain_name_length-2]+"."+arr_domain_name[domain_name_length-1]}else{return "."+arr_domain_name[domain_name_length-3]+"."+arr_domain_name[domain_name_length-2]+"."+arr_domain_name[domain_name_length-1]}}function ntes_set_cookie_long(name,value){var _ntes_epd=new Date();_ntes_epd.setTime(_ntes_epd.getTime()+1000*60*60*24*365*100);document.cookie=name+"="+value+";expires="+_ntes_epd.toGMTString()+";path=/;domain="+_ntes_cdmn}function ntes_set_cookie(name,value){var _ntes_epd=new Date();_ntes_epd.setTime(_ntes_epd.getTime()+0);document.cookie=name+"="+value+";path=/;domain="+_ntes_cdmn}function ntes_set_cookie_new(name,value,expires){if(!expires||expires==""){expires=1000*60*60*24*365*1}var _ntes_epd=new Date();_ntes_epd.setTime(_ntes_epd.getTime()+expires);document.cookie=name+"="+value+";expires="+_ntes_epd.toGMTString()+";path=/;domain="+_ntes_cdmn}function ntes_get_cookie(name){var _ntes_dc=document.cookie,_ntes_cname=name+"=",_ntes_clen=_ntes_dc.length,_ntes_cbegin=0;while(_ntes_cbegin<_ntes_clen){var _ntes_vbegin=_ntes_cbegin+_ntes_cname.length;if(_ntes_dc.substring(_ntes_cbegin,_ntes_vbegin)==_ntes_cname){var _ntes_vend=_ntes_dc.indexOf(";",_ntes_vbegin);if(_ntes_vend==-1)_ntes_vend=_ntes_clen;return unescape(_ntes_dc.substring(_ntes_vbegin,_ntes_vend))}_ntes_cbegin=_ntes_dc.indexOf(" ",_ntes_cbegin)+1;if(_ntes_cbegin==0)break}return-1}function ntes_get_flashver(){var f="",n=navigator;if(n.plugins&&n.plugins.length){for(var ii=0;ii<n.plugins.length;ii++){if(n.plugins[ii].name.indexOf('Shockwave Flash')!=-1){f=n.plugins[ii].description.split('Shockwave Flash')[1];break}}}else if(window.ActiveXObject){for(var ii=10;ii>=2;ii--){try{var fl=eval("new ActiveXObject('ShockwaveFlash.ShockwaveFlash."+ii+"');");if(fl){f=ii+'.0';break}}catch(e){}}}return f}var _ntes_hexcase=0,_ntes_chrsz=8;function ntes_hex_md5(s){return binl2hex(ntes_core_md5(str2binl(s),s.length*_ntes_chrsz))}function ntes_core_md5(x,len){x[len>>5]|=0x80<<((len)%32);x[(((len+64)>>>9)<<4)+14]=len;var a=1732584193,b=-271733879,c=-1732584194,d=271733878;for(var i=0;i<x.length;i+=16){var olda=a,oldb=b,oldc=c,oldd=d;a=md5_ff(a,b,c,d,x[i+0],7,-680876936);d=md5_ff(d,a,b,c,x[i+1],12,-389564586);c=md5_ff(c,d,a,b,x[i+2],17,606105819);b=md5_ff(b,c,d,a,x[i+3],22,-1044525330);a=md5_ff(a,b,c,d,x[i+4],7,-176418897);d=md5_ff(d,a,b,c,x[i+5],12,1200080426);c=md5_ff(c,d,a,b,x[i+6],17,-1473231341);b=md5_ff(b,c,d,a,x[i+7],22,-45705983);a=md5_ff(a,b,c,d,x[i+8],7,1770035416);d=md5_ff(d,a,b,c,x[i+9],12,-1958414417);c=md5_ff(c,d,a,b,x[i+10],17,-42063);b=md5_ff(b,c,d,a,x[i+11],22,-1990404162);a=md5_ff(a,b,c,d,x[i+12],7,1804603682);d=md5_ff(d,a,b,c,x[i+13],12,-40341101);c=md5_ff(c,d,a,b,x[i+14],17,-1502002290);b=md5_ff(b,c,d,a,x[i+15],22,1236535329);a=md5_gg(a,b,c,d,x[i+1],5,-165796510);d=md5_gg(d,a,b,c,x[i+6],9,-1069501632);c=md5_gg(c,d,a,b,x[i+11],14,643717713);b=md5_gg(b,c,d,a,x[i+0],20,-373897302);a=md5_gg(a,b,c,d,x[i+5],5,-701558691);d=md5_gg(d,a,b,c,x[i+10],9,38016083);c=md5_gg(c,d,a,b,x[i+15],14,-660478335);b=md5_gg(b,c,d,a,x[i+4],20,-405537848);a=md5_gg(a,b,c,d,x[i+9],5,568446438);d=md5_gg(d,a,b,c,x[i+14],9,-1019803690);c=md5_gg(c,d,a,b,x[i+3],14,-187363961);b=md5_gg(b,c,d,a,x[i+8],20,1163531501);a=md5_gg(a,b,c,d,x[i+13],5,-1444681467);d=md5_gg(d,a,b,c,x[i+2],9,-51403784);c=md5_gg(c,d,a,b,x[i+7],14,1735328473);b=md5_gg(b,c,d,a,x[i+12],20,-1926607734);a=md5_hh(a,b,c,d,x[i+5],4,-378558);d=md5_hh(d,a,b,c,x[i+8],11,-2022574463);c=md5_hh(c,d,a,b,x[i+11],16,1839030562);b=md5_hh(b,c,d,a,x[i+14],23,-35309556);a=md5_hh(a,b,c,d,x[i+1],4,-1530992060);d=md5_hh(d,a,b,c,x[i+4],11,1272893353);c=md5_hh(c,d,a,b,x[i+7],16,-155497632);b=md5_hh(b,c,d,a,x[i+10],23,-1094730640);a=md5_hh(a,b,c,d,x[i+13],4,681279174);d=md5_hh(d,a,b,c,x[i+0],11,-358537222);c=md5_hh(c,d,a,b,x[i+3],16,-722521979);b=md5_hh(b,c,d,a,x[i+6],23,76029189);a=md5_hh(a,b,c,d,x[i+9],4,-640364487);d=md5_hh(d,a,b,c,x[i+12],11,-421815835);c=md5_hh(c,d,a,b,x[i+15],16,530742520);b=md5_hh(b,c,d,a,x[i+2],23,-995338651);a=md5_ii(a,b,c,d,x[i+0],6,-198630844);d=md5_ii(d,a,b,c,x[i+7],10,1126891415);c=md5_ii(c,d,a,b,x[i+14],15,-1416354905);b=md5_ii(b,c,d,a,x[i+5],21,-57434055);a=md5_ii(a,b,c,d,x[i+12],6,1700485571);d=md5_ii(d,a,b,c,x[i+3],10,-1894986606);c=md5_ii(c,d,a,b,x[i+10],15,-1051523);b=md5_ii(b,c,d,a,x[i+1],21,-2054922799);a=md5_ii(a,b,c,d,x[i+8],6,1873313359);d=md5_ii(d,a,b,c,x[i+15],10,-30611744);c=md5_ii(c,d,a,b,x[i+6],15,-1560198380);b=md5_ii(b,c,d,a,x[i+13],21,1309151649);a=md5_ii(a,b,c,d,x[i+4],6,-145523070);d=md5_ii(d,a,b,c,x[i+11],10,-1120210379);c=md5_ii(c,d,a,b,x[i+2],15,718787259);b=md5_ii(b,c,d,a,x[i+9],21,-343485551);a=safe_add(a,olda);b=safe_add(b,oldb);c=safe_add(c,oldc);d=safe_add(d,oldd)}return Array(a,b,c,d)}function md5_cmn(q,a,b,x,s,t){return safe_add(bit_rol(safe_add(safe_add(a,q),safe_add(x,t)),s),b)}function md5_ff(a,b,c,d,x,s,t){return md5_cmn((b&c)|((~b)&d),a,b,x,s,t)}function md5_gg(a,b,c,d,x,s,t){return md5_cmn((b&d)|(c&(~d)),a,b,x,s,t)}function md5_hh(a,b,c,d,x,s,t){return md5_cmn(b^c^d,a,b,x,s,t)}function md5_ii(a,b,c,d,x,s,t){return md5_cmn(c^(b|(~d)),a,b,x,s,t)}function safe_add(x,y){var lsw=(x&0xFFFF)+(y&0xFFFF),msw=(x>>16)+(y>>16)+(lsw>>16);return(msw<<16)|(lsw&0xFFFF)}function bit_rol(num,cnt){return(num<<cnt)|(num>>>(32-cnt))}function str2binl(str){var bin=new Array(),mask=(1<<_ntes_chrsz)-1;for(var i=0;i<str.length*_ntes_chrsz;i+=_ntes_chrsz)bin[i>>5]|=(str.charCodeAt(i/_ntes_chrsz)&mask)<<(i%32);return bin}function binl2hex(binarray){var hex_tab=_ntes_hexcase?"0123456789ABCDEF":"0123456789abcdef",str="";for(var i=0;i<binarray.length*4;i++){str+=hex_tab.charAt((binarray[i>>2]>>((i%4)*8+4))&0xF)+hex_tab.charAt((binarray[i>>2]>>((i%4)*8))&0xF)}return str}function str_to_ent(str){var result='',i;for(i=0;i<str.length;i++){var c=str.charCodeAt(i),tmp='';if(c>255){while(c>=1){tmp="0123456789".charAt(c%10)+tmp;c=c/10}if(tmp==''){tmp="0"}tmp="#"+tmp;tmp="&"+tmp;tmp=tmp+";";result+=tmp}else{result+=str.charAt(i)}}return result}function ntes_page_click_stat(obj){var _ntes_a_h=escape(this.href),_ntes_a_t=escape(this.innerHTML),_ntes_p_url=escape(document.location),_ncw=0,_nmx=0,_nmy=0;if(document.documentElement&&document.documentElement.clientWidth){_ncw=document.documentElement.clientWidth}var evt=obj||window.event;if(evt.clientX&&document.documentElement){_nmx=evt.clientX+document.documentElement.scrollLeft;_nmy=evt.clientY+document.documentElement.scrollTop}var _ntes_p_q="_nacc="+_ntes_nacc+"&_npurl="+_ntes_p_url;_ntes_p_q+="&_nah="+_ntes_a_h+"&_nat="+_ntes_a_t;_ntes_p_q+="&_ncw="+_ncw+"&_nmx="+_nmx+"&_nmy="+_nmy;_ntes_p_q+="&_end";var i=new Image();i.src=_ntes_src_addr+"/ntes_p?"+_ntes_p_q;i.onload=function(){_ntes_void()};return true}function neteaseClickStat(){if(typeof(_ntes_nacc)=="undefined"||!_ntes_nacc){return}var _ntes_r=Math.random();_ntes_r=Math.round(_ntes_r*30);if(_ntes_r!=15)return;if(document.all&&navigator.userAgent.match(/msie/gi)){var _ntes_a_tag_array=document.getElementsByTagName('a');for(i in _ntes_a_tag_array){if(_ntes_a_tag_array[i].onclick==null){_ntes_a_tag_array[i].onclick=ntes_page_click_stat}}}}function recordAction(aName,vAddr,vName,p1,p2){var _q="";_q+="s="+_ntes_nacc;_q+="&u="+_ntes_nvid;_q+="&a="+escape(aName);_q+="&v="+escape(vAddr);_q+="&n="+escape(vName);_q+="&p1="+p1;if(p2!=undefined)_q+="&p2="+p2;_q+="&r="+_ntes_nurl;_q+="&_nxkey="+(Number(new Date())+''+Math.random()).substring(6,20)+"&_end1";var _img=new Image();_img.src=_ntes_src_addr+"/ntesv?"+_q;_img.onload=function(){_ntes_void()}}