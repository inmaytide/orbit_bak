docker run -itd -p 7001:7001 ^
        --name orbit-api-gateway ^
        --link orbit-configurations ^
        --link orbit-discovery ^
        --link orbit-i18n ^
        --link orbit-authorization ^
        orbit/api-gateway