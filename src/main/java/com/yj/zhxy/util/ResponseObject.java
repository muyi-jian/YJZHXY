package com.yj.zhxy.util;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
/**
 * @author YangJian
 * @date 2023/12/25 15:19
 * @description 全局统一返回结果类
 */
@Data
@Schema(defaultValue = "全局统一返回结果")
public class ResponseObject<T> {

    @Schema(defaultValue = "返回码")
    private Integer code;

    @Schema(defaultValue = "返回消息")
    private String message;

    @Schema(defaultValue = "返回数据")
    private T data;

    public ResponseObject(){}

    // 返回数据
    protected static <T> ResponseObject<T> build(T data) {
        ResponseObject<T> result = new ResponseObject<T>();
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static <T> ResponseObject<T> build(T body, ResponseCodeEnum resultCodeEnum) {
        ResponseObject<T> responseObject = build(body);
        responseObject.setCode(resultCodeEnum.getCode());
        responseObject.setMessage(resultCodeEnum.getMessage());
        return responseObject;
    }

    public static<T> ResponseObject<T> ok(){
        return ResponseObject.ok(null);
    }

    /**
     * 操作成功
     * @param data 数据
     * @param <T> 泛型
     * @return 具体返回
     */
    public static<T> ResponseObject<T> ok(T data){
        return build(data, ResponseCodeEnum.SUCCESS);
    }

    public static<T> ResponseObject<T> fail(){
        return ResponseObject.fail(null);
    }

    /**
     * 操作失败
     * @param data 数据
     * @param <T> 泛型
     * @return 具体返回
     */
    public static<T> ResponseObject<T> fail(T data){
        return build(data, ResponseCodeEnum.FAIL);
    }

    public ResponseObject<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public ResponseObject<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        return this.getCode().intValue() == ResponseCodeEnum.SUCCESS.getCode().intValue();
    }
}
