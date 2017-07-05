package com.example.e_tecklaptop.testproject.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by E-Teck Laptop on 7/4/2017.
 */

public class SendEmailApi {
        @SerializedName("result")
        @Expose
        private String result;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }


}
