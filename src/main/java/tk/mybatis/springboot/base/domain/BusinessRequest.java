package tk.mybatis.springboot.base.domain;

import java.io.Serializable;

public abstract class BusinessRequest<T extends BusinessReponse>
        implements Serializable
{
    private static final long serialVersionUID = 1L;
}
