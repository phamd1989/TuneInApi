package com.example.dungpham.tuneintest.model;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by dungpham on 4/5/16.
 */
public class ErrorFuc1Handler implements Func1<ElementWrapper, Observable<ElementWrapper>> {
    @Override
    public Observable<ElementWrapper> call(ElementWrapper elementWrapper) {
        if (elementWrapper.getBody() == null) {
            return Observable.error(new ApiException());
        } else {
            return Observable.just(elementWrapper);
        }
    }
}
