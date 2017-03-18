package com.pietrantuono.podcasts.providers;


import com.pietrantuono.podcasts.singlepodcast.model.SimpleEnclosure;
import com.rometools.rome.feed.synd.SyndEnclosure;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class RealmUtlis {

    public static List<String> toStringList(RealmList<RealmString> realmList) {
        List<String> list = new ArrayList<>(realmList.size());
        for (RealmString s : realmList) {
            list.add(s.getString());
        }
        return list;
    }

    public static RealmList<RealmString> toRealmStringList(List<String> list) {
        RealmList<RealmString> realmStrings = new RealmList<RealmString>();
        for (String s : list) {
            realmStrings.add(new RealmString(s));
        }
        return realmStrings;
    }

    public static List<SyndEnclosure> toEnclosureList(List<SimpleEnclosure> syndEnclosures) {
        return null;
    }
}
