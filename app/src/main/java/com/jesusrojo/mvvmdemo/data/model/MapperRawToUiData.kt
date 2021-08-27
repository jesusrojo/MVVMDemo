package com.jesusrojo.mvvmdemo.data.model


class MapperRawToUiData: Function1<List<RawData>, List<UiData>> {

    override fun invoke(rawDatas: List<RawData>): List<UiData> {
        return rawDatas.map {

            var dataId: Int = -1
            val dataIdRaw = it.id
            if (dataIdRaw != null) dataId = dataIdRaw

            var name = ""
            val nameRaw = it.name
            if (nameRaw != null) name = nameRaw

            var avatarUrl = ""
            val avatarUrlRaw = it.owner?.avatarUrl
            if (avatarUrlRaw != null) avatarUrl = avatarUrlRaw

            var title = ""
            val titleRaw = it.owner?.ownerName
            if (titleRaw != null) title = titleRaw

            var description = ""
            val descriptionRaw = it.description
            if (descriptionRaw != null) description = descriptionRaw

            var fullDescription = ""
            val fullDescriptionRaw = it.toString()
            if (fullDescriptionRaw != null) fullDescription = fullDescriptionRaw

            var forksCount: Int = -1
            val forksCountRaw = it.forksCount
            if (forksCountRaw != null) forksCount = forksCountRaw

            var startsCount: Int = -1
            val startsCountRaw = it.stargazersCount
            if (startsCountRaw != null) startsCount = startsCountRaw

            UiData(dataId, name, avatarUrl, title, description,
                fullDescription, forksCount, startsCount)
        }
    }
}