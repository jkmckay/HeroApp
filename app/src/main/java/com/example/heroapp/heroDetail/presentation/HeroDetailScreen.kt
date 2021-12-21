package com.example.heroapp.heroDetail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.heroapp.R
import com.example.heroapp.herolist.domain.model.HeroItem

@ExperimentalCoilApi
@Composable
fun HeroDetailScreen(
    navController: NavController,
    viewModel: HeroDetailViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(R.string.hero_list_top_bar_title)) }) },
        content = { HeroDetailContent(viewModel) }
    )
}

@ExperimentalCoilApi
@Composable
fun HeroDetailContent(viewModel: HeroDetailViewModel) {
    val heroDetailState by viewModel.state.collectAsState()
    when (val state = heroDetailState) {
        HeroDetailState.Error -> Text("Error!")
        HeroDetailState.Loading -> LoadingContent()
        is HeroDetailState.Success -> HeroDetail(state.hero)
    }
}

@ExperimentalCoilApi
@Composable
fun HeroDetail(hero: HeroItem) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(240.dp),
                painter = rememberImagePainter(
                    data = hero.detailImage,
                    builder = {
                        placeholder(R.drawable.ic_hero)
                        error(R.drawable.ic_load_error)
                    }
                ),
                contentDescription = stringResource(R.string.hero_list_item_content_desc)

            )
        }
        Text(
            text = stringResource(
                R.string.hero_list_item_hero_name,
                hero.name
            ),
            style = MaterialTheme.typography.h4
        )
        if (hero.fullName.isNotEmpty()) {
            Text(
                text = stringResource(
                    R.string.hero_list_item_name,
                    hero.fullName
                ),
                style = MaterialTheme.typography.h6
            )
        }
        Text(
            text = stringResource(
                R.string.hero_list_item_first_appearance,
                hero.firstAppearance
            ),
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
