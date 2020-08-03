
import {config} from './config'
import Cookies from 'js-cookie';
import {Base64} from 'js-base64';
import Qs from 'qs';
import Ajax from './ajax';


export default class OAuth {

    constructor(options) {
        this.options = options;
        const ajaxOptions = Object.assign({}, {
            baseURL: config.oauth.baseURL,
            withCredentials: true
            // transformRequest:[(data)=>{
            //
            // }]
        });
        console.log(ajaxOptions)
        this.ajax = new Ajax(ajaxOptions);
    }

    /**
     * 授权
     */
    authorize(scope){
        // 获取token
        const token = Cookies.get('ztt');
        if (token){
            return;
        }
        const queryStr = location.search.substring(1);
        const params = Qs.parse(queryStr);
        if (params.code){
            this.generateToken(params.code);
            return;
        }
        this.generateAuthorize(scope);
    }

    /**
     * 跳转授权页面
     */
    generateAuthorize(scope) {
        const tempObj = {
            client_id: this.options.appId,
            response_type: 'code',
            redirect_uri: location.href,
            scope: scope
        };
        const params = Qs.stringify(tempObj);
        location.href = config.oauth.authorizeUrl + "?" + params;
    }

    /**
     * 获取token
     */
    generateToken(code){
        const str =  this.options.appId + ":" + this.options.appSecret;
        console.log(str)
        const basic = Base64.encode(str);
        console.log(basic)
        const headers = {
            'Authorization': 'Basic ' + basic,
            'Content-Type': 'multipart/form-data;boundary = ' + new Date().getTime()
        };
        let formData = new FormData();
        formData.append("code", code);
        formData.append("grant_type", 'authorization_code');
        formData.append("redirect_uri", 'http://www.baidu.com/');
       this.ajax.axios.post(
           config.oauth.accessTokenUrl,
           formData ,
           {headers: headers}
       ).then(res=>{
           console.log(res)
       }).catch(err=>{
           console.log(err)
       })
    }

}