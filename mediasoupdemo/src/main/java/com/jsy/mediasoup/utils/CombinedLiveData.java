package com.jsy.mediasoup.utils;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

@SuppressWarnings("unused")
public class CombinedLiveData<A, B, OUTPUT> extends MediatorLiveData<OUTPUT> {

    public interface Combiner<T, K, S> {
        S combine(T t, K k);
    }

    private A input1;
    private B mB;

    public CombinedLiveData(
        LiveData<A> source1, LiveData<B> source2, @NonNull Combiner<A, B, OUTPUT> combiner) {
        super.addSource(
                source1,
                (data1) -> {
                    input1 = data1;
                    setValue(combiner.combine(input1, mB));
                });

        super.addSource(
                source2,
                (data2) -> {
                    mB = data2;
                    setValue(combiner.combine(input1, mB));
                });
    }

    @Override
    public <S1> void addSource(@NonNull LiveData<S1> source, @NonNull Observer<S1> onChanged) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S1> void removeSource(@NonNull LiveData<S1> toRemote) {
        throw new UnsupportedOperationException();
    }

}
