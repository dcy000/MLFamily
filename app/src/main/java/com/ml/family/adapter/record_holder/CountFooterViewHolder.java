/*
 * Copyright (C) 2015 Tomás Ruiz-López.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ml.family.adapter.record_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ml.family.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tomas on 15/07/15.
 */
public class CountFooterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.foot)
    View line;

    public CountFooterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void render(boolean islast){
        if(islast){
//            line.setVisibility(View.GONE);
        }
    }
}
