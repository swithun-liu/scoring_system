/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-27 13:36:47
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-27 13:38:45
 */
package com.swithun.backend.dto;

public class AddFileFileListDTO {
    
    public AddFileFileListDTO(String name, String url) {
        this.name = name;
        this.url = url;
    }
    public AddFileFileListDTO() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    private String name;
    private String url;
}
/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-27 13:36:47
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-27 13:36:48
 */
