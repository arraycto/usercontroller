import {config} from "./config";
import OAuth from './OAuth';

export default {

    config: (options)=>{
        return {
            oauth: new OAuth(options)
        }
    }

}
