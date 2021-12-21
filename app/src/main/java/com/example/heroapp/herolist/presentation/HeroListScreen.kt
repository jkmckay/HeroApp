package com.example.heroapp.herolist.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
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
fun HeroListScreen(navController: NavController, viewModel: HeroListViewModel = hiltViewModel()) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(R.string.hero_list_top_bar_title)) }) },
        content = {
            HeroListContent(viewModel) { heroId ->
                navController.navigate(route = "heroDetail/$heroId")
            }
        }
    )
}

@ExperimentalCoilApi
@Composable
fun HeroListContent(viewModel: HeroListViewModel, onHeroClick: (heroId: Int) -> Unit) {
    val viewState by viewModel.state.collectAsState()

    when (val state = viewState) {
        HeroListState.Empty -> NoContent()
        is HeroListState.Error -> ErrorContent(viewModel::getHeroes)
        HeroListState.Loading -> LoadingContent()
        is HeroListState.Success -> HeroList(state.heroes, onHeroClick)
    }
}

@ExperimentalCoilApi
@Composable
fun HeroList(heroes: List<HeroItem>, onHeroClick: (heroId: Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 16.dp),
        content = {
            items(heroes) { HeroListItem(it, onHeroClick) }
        }
    )
}

@ExperimentalCoilApi
@Composable
fun HeroListItem(hero: HeroItem, onHeroClick: (heroId: Int) -> Unit) {
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onHeroClick(hero.id) }
            .border(
                color = colorResource(R.color.light_grey),
                width = 1.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = MaterialTheme.colors.background,
                RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            contentScale = ContentScale.Crop,
            painter = rememberImagePainter(
                data = hero.listImage,
                builder = {
                    placeholder(R.drawable.ic_hero)
                    error(R.drawable.ic_load_error)
                }
            ),
            contentDescription = stringResource(R.string.hero_list_item_content_desc),
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(
                    R.string.hero_list_item_hero_name,
                    hero.name
                ),
                style = MaterialTheme.typography.body1
            )
            hero.publisher?.let {
                Text(
                    text = stringResource(
                        R.string.hero_list_item_publisher,
                        hero.publisher
                    ),
                    style = MaterialTheme.typography.body2
                )
            } ?: run {
                Text(
                    text = stringResource(
                        R.string.hero_list_item_no_publisher,
                    ),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorContent(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.hero_list_error_title),
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onRetry() }) {
            Text(text = stringResource(R.string.hero_list_error_button_text))
        }
    }
}

@Composable
fun NoContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.hero_list_no_content_text),
            style = MaterialTheme.typography.h2
        )
    }
}
