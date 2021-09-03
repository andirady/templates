import type {
    RestResponse,
} from "../../../target/typescript-generator/backend";
import { RestApplicationClient } from "../../../target/typescript-generator/backend";

export function createRestClient() {
    return new RestApplicationClient({
        async request<R>(config: {
            method: string;
            url: string;
            queryParams?: any;
            data?: any;
            copyFn?: (data: R) => R;
        }): RestResponse<R> {
            let url = `api/${config.url}`;
            if (config.queryParams) {
                url = `${url}?${config.queryParams}`;
            }
            let method = config.method;
            let headers = {};
            let body: string = null;

            if (config.data) {
                body = JSON.stringify(config.data);
                headers['Content-Type'] = 'application/json';
            }

            let resp: Response = await fetch(url, { method, headers, body });
            return await resp.json();
        },
    });
}
