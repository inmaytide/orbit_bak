package com.inmaytide.orbit;

public interface Constants {

    // 用于内部服务之间一些需要匿名请求的Token
    String ANONYMOUS_TOKEN_VALUE = "MmY3ZDA4YjY1MDA2OGUzZDA2YWVmZTY3MTc0NjFlOTc0ZGEzZTJiNTc3ZDM4NDJhNmUyMDRjMGZjMDQ2YTM1ZQ";

    String TOKEN_NAME = "access_token";

    String HEADER_ANONYMOUS_TOKEN = "Cookie=" + TOKEN_NAME + "=" + ANONYMOUS_TOKEN_VALUE;
}
